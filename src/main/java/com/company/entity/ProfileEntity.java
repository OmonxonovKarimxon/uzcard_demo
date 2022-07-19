package com.company.entity;

import com.company.enums.CompanyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String contact;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column
    private Boolean visible;
    @Column
    @Enumerated(EnumType.STRING)
    private CompanyType type;
}