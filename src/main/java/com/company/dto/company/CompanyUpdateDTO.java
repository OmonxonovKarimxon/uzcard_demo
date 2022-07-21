package com.company.dto.company;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyUpdateDTO {
//   id(uuid), name, address,contact,created_date,visible, 
//   role(BANK,PAYMENT), username (unique),password

    private String id;
    private String name;
    private String address;
    private String contact;
    private String password;


}
