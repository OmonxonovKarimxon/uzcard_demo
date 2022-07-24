package com.company.dto.transfer;

import com.company.enums.TransferStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferResponseDTO {
    private TransferStatus status;
    private String message;
    private TransferDTO transfer;

    public TransferResponseDTO(TransferStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public TransferResponseDTO(TransferStatus status, String message, TransferDTO transfer) {
        this.status = status;
        this.message = message;
        this.transfer = transfer;
    }
    public TransferResponseDTO(TransferStatus status, TransferDTO transfer) {
        this.status = status;

        this.transfer = transfer;
    }
}
