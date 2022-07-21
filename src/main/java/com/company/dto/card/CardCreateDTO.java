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
public class CardCreateDTO {
    private String clientId;
}
