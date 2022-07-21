package com.company.dto.client;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateDTO {
    private String id;
    private String name;
    private String surname;
}
