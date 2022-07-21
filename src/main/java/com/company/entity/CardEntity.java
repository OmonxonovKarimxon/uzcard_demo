package com.company.entity;

import com.company.enums.CardStatus;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card")
public class CardEntity extends  BaseEntity {
//  id(uuid),number,expired_date,phone,status(ACTIVE,BLOCK,NO_ACTIVE),created_date,balance, client_id,company_id,

    @Column
    private String number;

    @Column(name = "hidden_number",nullable = false)
    private String hiddenNumber;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @Column
    private Long balance;

    @Column(name = "client_id")
    private String clientId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false,updatable = false,insertable = false)
    private ClientEntity client;

    @Column(name = "company_id")
    private String companyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false,updatable = false,insertable = false)
    private CompanyEntity company;
}