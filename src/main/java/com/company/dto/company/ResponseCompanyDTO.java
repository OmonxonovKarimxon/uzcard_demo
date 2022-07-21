package com.company.dto.company;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCompanyDTO {


    private String id;
    private String name;
    private String address;
    private String contact;
    private GeneralRole role;
    private GeneralStatus status;
    private String username;
    private String password;


}
