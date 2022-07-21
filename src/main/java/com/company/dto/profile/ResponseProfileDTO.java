package com.company.dto.profile;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProfileDTO {
    //  id(uuid),name,surname,created_date,status,role(ADMIN,MODERATOR), username,password
    private String id;
    private String name;

    private String surname;

    private GeneralRole role;

    private GeneralStatus status;

    private String username;
    private String password;

}
