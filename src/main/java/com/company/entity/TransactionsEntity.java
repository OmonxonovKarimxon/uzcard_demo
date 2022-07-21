package com.company.entity;

import com.company.enums.TransactionStatus;
import com.company.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Transactions")
public class TransactionsEntity extends BaseEntity {
//  id(uuid), card_id,amount,transaction_type (CREDIT,DEBIT), transfer_id, created_date,
//        status(CREATED,SUCCESS,FAILED,CANCELED,)

    @Column(name = "card_id")
    private String cardId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, updatable = false, insertable = false)
    private CardEntity card;


    @Column
    private long amount;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column
    private Long balance;

    @Column(name = "transfer_id")
    private String transferId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", nullable = false, updatable = false, insertable = false)
    private TransferEntity transfer;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}