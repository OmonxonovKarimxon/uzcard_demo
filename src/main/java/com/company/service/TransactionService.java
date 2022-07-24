package com.company.service;

import com.company.dto.TransactionDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.entity.TransactionsEntity;
import com.company.entity.TransferEntity;
import com.company.exps.ItemNotFoundException;
import com.company.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public TransactionDTO entityToDTO(TransactionsEntity entity) {

        TransactionDTO dto =new TransactionDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());
      dto.setCardId(entity.getCardId());
        dto.setType(String.valueOf(entity.getType()));
        dto.setTransferId(entity.getTransferId());

        return dto;
    }

    public TransactionsEntity get(String id) {
        return transactionRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("transfer Not found");
        });
    }

    public PageImpl<TransactionDTO> pagination(int page, int size, String cardId) {

        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.findByCardId(cardId,pageable);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> byProfileId(int page, int size, String profileId) {
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.byProfileId(profileId,pageable);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> byPhoneNumber(int page, int size, String byPhoneNumber) {
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.byPhoneNumber(byPhoneNumber,pageable);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> debit(int page, int size, String cardId) {
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.debit(cardId,pageable);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());
    }

    public PageImpl<TransactionDTO> credit(int page, int size, String cardId) {
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.credit(cardId,pageable);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());
    }


    public PageImpl<TransactionDTO> month(int page, int size, String cardId) {
        LocalDateTime time = LocalDateTime.now().minusMonths(1);
        Pageable pageable = PageRequest.of(page,size);
        Page<TransactionsEntity> transactionsEntityPage = transactionRepository.month(cardId,pageable,time);
        List<TransactionDTO> dtoList = new ArrayList<>();
        transactionsEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl<>(dtoList,pageable,transactionsEntityPage.getTotalElements());

    }
}
