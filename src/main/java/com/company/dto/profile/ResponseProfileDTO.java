package com.company.dto.profile;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistretionProfileDTO {
//  id(uuid),name,surname,created_date,status,role(ADMIN,MODERATOR), username,password
    @NotNull(message = "name is null")
    private String name;
    @NotNull(message = "surname is null")
    private String surname;
    @NotNull(message = "role is null")
    private GeneralRole role;
    @NotNull(message = "status is null")
    private GeneralStatus status;
    @NotNull(message = "username is null")
    private String username;
    @NotNull(message = "password is null")
    private String password;

}
