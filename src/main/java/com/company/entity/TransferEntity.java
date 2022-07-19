package com.company.entity;

import com.company.enums.CompanyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card")
public class CardEntity extends  BaseEntity {
// id(uuid),number,expired_date,phone,status(ACTIVE,BLOCK,NO_ACTIVE),created_date,balance, profile_id,
    @Column
    private String number;
    @Column
    private LocalDateTime expired_date;
    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private CompanyType type;
    @Column
    private Long balans;

    @Column(name = "profile_id")
    private Integer profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false,updatable = false,insertable = false)
    private ProfileEntity profile;
}