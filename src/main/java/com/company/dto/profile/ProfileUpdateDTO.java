package com.company.dto.profile;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProfileUpdateDTO {
//  id(uuid),name,surname,created_date,status,role(ADMIN,MODERATOR), username,password

    private String id;
    private String name;
    private String surname;
    private String password;

}
