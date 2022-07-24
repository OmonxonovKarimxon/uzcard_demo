package com.company.repository;

import com.company.entity.TransactionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionsEntity, String> {


    Page<TransactionsEntity> findByCardId(String hisobraqam, Pageable pageable);

    @Query("from TransactionsEntity tr where tr.card.clientId=:profileId")
    Page<TransactionsEntity> byProfileId(String profileId, Pageable pageable);

    @Query("from TransactionsEntity tr where tr.card.phone=:byPhoneNumber")
    Page<TransactionsEntity> byPhoneNumber(String byPhoneNumber, Pageable pageable);

    @Query("from TransactionsEntity tr where tr.cardId=:cardId and tr.type='DEBIT'")
    Page<TransactionsEntity> debit(String cardId, Pageable pageable);

    @Query("from TransactionsEntity tr where tr.cardId=:cardId and tr.type='CREDIT'")
    Page<TransactionsEntity> credit(String cardId, Pageable pageable);

    @Query(value = "from TransactionsEntity tr where tr.cardId=:cardId and tr.createdDate>:time ")
    Page<TransactionsEntity> month(String cardId, Pageable pageable, LocalDateTime time);
}
