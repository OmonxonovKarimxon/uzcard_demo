package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.TransactionDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.entity.CardEntity;
import com.company.entity.CompanyEntity;
import com.company.entity.TransactionsEntity;
import com.company.entity.TransferEntity;
import com.company.enums.*;
import com.company.exps.ItemNotFoundException;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.repository.TransferFilterRepository;
import com.company.repository.TransferRepository;
import com.company.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransferFilterRepository transferFilterRepository;


    private final double uzCardServicePercentage = 0.5;

    public TransferResponseDTO create(TransferCreateDTO transferDTO) {

        CardEntity fromCard = cardService.getByCardNumber(transferDTO.getFromCard());
        CardEntity toCard = cardService.getByCardNumber(transferDTO.getToCard());
        String fullName = toCard.getClient().getName() + " " + toCard.getClient().getSurname();
        CompanyEntity company = companyService.get(transferDTO.getCompanyId());

        if (!fromCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO(TransferStatus.FAILED, "Card not active");
        }

        if (!toCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO(TransferStatus.FAILED, "Card not active");
        }

        // 10,000
        //  company.getServicePercentage()  0.3
        //  0.4 - uzcard
        double service_percentage = uzCardServicePercentage + company.getServicePercentage(); // 0.7
        double service_amount = (transferDTO.getAmount() * service_percentage) / 100; // 70
        double total_amount = transferDTO.getAmount() + service_amount; // 10,070

        if (fromCard.getBalance() < total_amount) {
            return new TransferResponseDTO(TransferStatus.FAILED, "Not enough money");
        }

        TransferEntity entity = new TransferEntity();
        entity.setAmount(transferDTO.getAmount());
        entity.setFromCardId(fromCard.getId());
        entity.setToCardId(toCard.getId());
        entity.setServiceAmount((long) service_amount);
        entity.setTotalAmount((long) total_amount);
        entity.setCompanyId(transferDTO.getCompanyId());
        entity.setStatus(TransferStatus.IN_PROGRESS);
        transferRepository.save(entity);


        TransferDTO dto = new TransferDTO();
        dto.setId(entity.getId());
        dto.setFromCard(fromCard.getHiddenNumber());
        dto.setWhom(toCard.getClient().getSurname() + " " + toCard.getClient().getName());
        dto.setAmount(entity.getAmount());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setServiceAmount(entity.getServiceAmount());
        dto.setCreateDate(entity.getCreatedDate());
        dto.setProvayder(entity.getCompanyId());

        return new TransferResponseDTO(TransferStatus.IN_PROGRESS, "tasdiqlang", dto);
    }


    public void finishTransfer(String id) {
        TransferEntity entity = get(id);
        // from card credit
        cardRepository.fromCardCredit(entity.getFromCardId(), entity.getTotalAmount());

        //tocard debit
        cardRepository.toCardDebit(entity.getToCardId(), entity.getAmount());


        // DEBIT of company
        CompanyEntity company = companyService.get(entity.getCompanyId());
        Long companyAmount = (long) (entity.getAmount() * company.getServicePercentage()) / 100;
        cardRepository.debitCompanyCard(companyAmount, company.getCardId());

        // DEBIT OF uzCard
        CompanyEntity uzCard = companyService.getByUsername("UzCard");
        Long uzCardAmount = (long) (entity.getAmount() * uzCard.getServicePercentage()) / 100;
        cardRepository.debitToUzCard(uzCardAmount, uzCard.getCardId());


        TransactionsEntity fromCardAction = new TransactionsEntity();
        fromCardAction.setCardId(entity.getFromCardId());
        fromCardAction.setAmount(entity.getTotalAmount());
        fromCardAction.setType(TransactionType.CREDIT);
        fromCardAction.setTransferId(entity.getId());
        fromCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(fromCardAction);


        TransactionsEntity toCardAction = new TransactionsEntity();
        toCardAction.setCardId(entity.getToCardId());
        toCardAction.setAmount(entity.getAmount());
        toCardAction.setType(TransactionType.DEBIT);
        toCardAction.setTransferId(entity.getId());
        toCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(toCardAction);


        TransactionsEntity companyAction = new TransactionsEntity();
        companyAction.setCardId(company.getCardId());
        companyAction.setAmount(companyAmount);
        companyAction.setType(TransactionType.DEBIT);
        companyAction.setTransferId(entity.getId());
        companyAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(companyAction);


        TransactionsEntity uzCardAction = new TransactionsEntity();
        uzCardAction.setCardId(uzCard.getCardId());
        uzCardAction.setAmount(uzCardAmount);
        uzCardAction.setType(TransactionType.DEBIT);
        uzCardAction.setTransferId(entity.getId());
        uzCardAction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(uzCardAction);


        transferRepository.changeStatus(TransferStatus.SUCCESS, entity.getId());
    }


    public TransferDTO getById(String id) {
        CustomUserDetails currentUser = SpringUtil.currentUser();
        TransferEntity transferEntity = get(id);
        if (currentUser.getRole().equals(GeneralRole.ROLE_USER) &&
                currentUser.getId().equals(transferEntity.getToCard().getClient().getId())) {
            return entityToDTO(get(id));
        }
        if (!currentUser.getRole().equals(GeneralRole.ROLE_USER)) {
            return entityToDTO(get(id));
        }
        return null;
    }


    public TransferEntity get(String id) {
        return transferRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("transfer Not found");
        });
    }

    public TransferDTO entityToDTO(TransferEntity entity) {

        TransferDTO dto = new TransferDTO();
        dto.setId(entity.getId());
        dto.setFromCard(entity.getFromCard().getHiddenNumber());
        dto.setWhom(entity.getToCard().getClient().getSurname() + " " + entity.getToCard().getClient().getName());
        dto.setAmount(entity.getAmount());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setServiceAmount(entity.getServiceAmount());
        dto.setCreateDate(entity.getCreatedDate());
        dto.setProvayder(entity.getCompanyId());
        return dto;
    }


    public PageImpl<TransferDTO> pagination(int page, int size, TransferDTO dto) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransferEntity> filter = transferFilterRepository.filter(dto, pageable);
        List<TransferDTO> dtoList = new ArrayList<>();
        filter.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList, pageable, filter.getTotalElements());

    }
}
