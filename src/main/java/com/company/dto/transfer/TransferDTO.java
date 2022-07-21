package com.company.dto.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDTO {

    private String id;
    private String fromCard;
    private String whom;
    private Long amount;
    private Long serviceAmount;
    private Long totalAmount;
    private LocalDateTime createDate;
    private String provayder;
}
