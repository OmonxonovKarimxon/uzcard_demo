package com.company.entity;

import com.company.entity.BaseEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
//   id(uuid),name,surname,created_date,status,role(ADMIN,MODERATOR), username,password

    @Column(nullable = false)
    private String name;

    @Column
    private String surname;


    @Column(nullable = false)
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "company_id")
    private Integer companyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyEntity company;

}