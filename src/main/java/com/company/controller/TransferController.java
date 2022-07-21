package com.company.controller;

import com.company.dto.card.CardCreateDTO;
import com.company.dto.card.CardDTO;
import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/user/create")
    public ResponseEntity<TransferResponseDTO> create(@RequestBody TransferCreateDTO dto){
        TransferResponseDTO transferResponseDTO = transferService.create(dto);
        return ResponseEntity.ok().body(transferResponseDTO);
    }
    @PutMapping("/user/finish/{id}")
    public ResponseEntity<String> create(@PathVariable("id") String id){
        String response = transferService.finishTransfer(id);
        return ResponseEntity.ok().body(response);
    }


}
