package com.company.dto.client;

import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDTO {

    private String id;
    private GeneralStatus status;
    private String passportNumber;
    private String passportSeria;
    private String phone;
    private String name;
    private String middleName;
    private String surname;
}
