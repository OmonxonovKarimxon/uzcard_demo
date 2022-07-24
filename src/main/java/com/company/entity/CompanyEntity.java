package com.company.entity;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "company")
public class CompanyEntity extends BaseEntity {
    //    id(uuid), name, address,contact,created_date,visible, role(BANK,PAYMENT), username (unique),password

    @Column
    private String name;
    @Column
    private String address;

    @Column(unique = true)
    private String username;

    @Column
    private String password;


    @Column
    private String contact;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralRole role;

    @Column() // nullable = false
    private Double servicePercentage;

    @Column(name = "card_id")
    private String cardId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, updatable = false, insertable = false)
    private CardEntity  Card;
}