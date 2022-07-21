package com.company.dto;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String id;
    private String role;
    private String username;
    private String password;
    private String status;
    private Boolean visible;


}
