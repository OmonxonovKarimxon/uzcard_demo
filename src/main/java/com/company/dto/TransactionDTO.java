package com.company.dto;

import com.company.dto.transfer.TransferDTO;
import com.company.entity.TransferEntity;
import com.company.enums.TransactionStatus;
import com.company.enums.TransactionType;
import com.company.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {

    private String id;
    private String cardId;
    private long amount;
    private String type;
    private String transferId;
    private TransactionStatus status;
    private LocalDateTime createdDate;
}
