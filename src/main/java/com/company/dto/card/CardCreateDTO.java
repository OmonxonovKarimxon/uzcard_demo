package com.company.dto.card;

import com.company.dto.client.ClientResponseDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
    private String id;
    private String number;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private String phone;
    private Long balance;
    private String clientId;
    private ClientResponseDTO client;
    private String companyId;
    private ResponseCompanyDTO company;
    private GeneralStatus status;
}
