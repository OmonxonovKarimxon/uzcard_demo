package com.company.entity;

import com.company.enums.CardStatus;
import com.company.enums.CompanyType;
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
// service_percentage(1%),created_date, status(SUCCESS,FAILED,CANCELED), company_id

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

    @Column
    private LocalDateTime expired_date;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    @Column
    private Long balance;

    @Column(name = "profile_id")
    private String profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, updatable = false, insertable = false)
    private ProfileEntity profile;
}