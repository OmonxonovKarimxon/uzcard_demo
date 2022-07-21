package com.company.entity;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
//   Profile - bular uzcard ni odamlari.
//     id(uuid),name,surname,created_date,status,role(ADMIN,MODERATOR), username,password

    @Column(nullable = false)
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column()
    @Enumerated(EnumType.STRING)
    private GeneralRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;



}