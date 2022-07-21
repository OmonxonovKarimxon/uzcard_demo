package com.company.entity;

import com.company.enums.CardStatus;
import com.company.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transfer")
public class TransferEntity extends BaseEntity {
// id (uuid), from_card_id, to_card_id, total_amount(5600),amount(5500),service_amount(100),
//     service_percentage(1%),created_date, status(SUCCESS,FAILED,CANCELED), company_id

    @Column(name = "from_card_id")
    private String fromCardId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_card_id", nullable = false, updatable = false, insertable = false)
    private CardEntity fromCard;

    @Column(name = "to_card_id")
    private String toCardId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_card_id", nullable = false, updatable = false, insertable = false)
    private CardEntity toCard;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column()
    private Long amount;

    @Column(name = "service_amount")
    private Long  serviceAmount;

    @Column()
    private LocalDateTime expired_date;


    @Column()
    @Enumerated(EnumType.STRING)
    private TransferStatus status;


    @Column(name = "company_Id")
    private String companyId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id", nullable = false, updatable = false, insertable = false)
    private CompanyEntity company;
}