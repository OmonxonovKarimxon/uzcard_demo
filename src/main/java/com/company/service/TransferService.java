package com.company.service;

import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.entity.CardEntity;
import com.company.entity.CompanyEntity;
import com.company.entity.TransferEntity;
import com.company.enums.GeneralStatus;
import com.company.enums.TransferStatus;
import com.company.exps.ItemNotFoundException;
import com.company.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CompanyService companyService;

    private final double uzCardServicePercentage = 0.005;

    public TransferResponseDTO create(TransferCreateDTO transferDTO) {

        CardEntity fromCard = cardService.getByCardNumber(transferDTO.getFromCard());
        CardEntity toCard = cardService.getByCardNumber(transferDTO.getToCard());
      String fullName  =  toCard.getClient().getName()+" "+toCard.getClient().getSurname();
        CompanyEntity company = companyService.get(transferDTO.getCompanyId());

        if (!fromCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO("failed", "Card not active");
        }

        if (!toCard.getStatus().equals(GeneralStatus.ACTIVE)) {
            return new TransferResponseDTO("failed", "Card not active");
        }

        // 10,000
        //  company.getServicePercentage()  0.3
        //  0.4 - uzcard
        double service_percentage = uzCardServicePercentage + company.getServicePercentage(); // 0.7
        double service_amount = transferDTO.getAmount() * service_percentage; // 70
        double total_amount = transferDTO.getAmount() + service_amount; // 10,070

        if (fromCard.getBalance() < total_amount) {
            return new TransferResponseDTO("failed", "Not enough money");
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
       dto.setWhom(fullName);
       dto.setAmount(entity.getAmount());
       dto.setTotalAmount(entity.getTotalAmount());
       dto.setServiceAmount(entity.getServiceAmount());
       dto.setCreateDate(entity.getCreatedDate());
       dto.setProvayder(entity.getCompanyId());


        return new TransferResponseDTO("success", "tasdiqlang", dto);
    }

    public String finishTransfer(String id) {

        CompanyEntity uzCard = companyService.getByUsername("UzCard");

        Optional<TransferEntity> optional = transferRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("transaction not found");
        }
        TransferEntity transferEntity = optional.get();
        String companyId = transferEntity.getCompanyId();



        // 1. fromCard - CREDIT
        // 2. TOCARD - DEBIT
        // 3. card_id (PAYMENT,BANK) - DEBIT
        // 4. card_id  (UZ_CARD) - DEBIT

        // FROM_CARD_UPDATE
        // TO_CARD_UPDATE
        // Company_CARD_UPDATE
        // Uzcard_CARD_UPDATE

        // Transfer status update
        return null;
    }


}
