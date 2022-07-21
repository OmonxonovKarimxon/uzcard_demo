package com.company.entity;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity extends BaseEntity {
//  id(uuid),name,surname,middle_name,created_date, phone(not unique), status, password_seria,passport_number
//     -> ( passport_seria + passport_number  unique)
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","university_id"}))
    @Column(nullable = false)
    private String name;

    @Column
    private String surname;
    @Column(name = "middle_name")
    private String middleName;


    @Column(nullable = false)
    private String phone;

    @Column(name = "passport_seria")
    private String passportSeria;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

}