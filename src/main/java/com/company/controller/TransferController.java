package com.company.controller;

import com.company.dto.TransactionDTO;
import com.company.dto.card.CardCreateDTO;
import com.company.dto.card.CardDTO;
import com.company.dto.transfer.TransferCreateDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.dto.transfer.TransferResponseDTO;
import com.company.service.TransferService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/all/create")
    public ResponseEntity<TransferResponseDTO> create(@RequestBody TransferCreateDTO dto) {
        TransferResponseDTO transferResponseDTO = transferService.create(dto);
        return ResponseEntity.ok().body(transferResponseDTO);
    }

    @PutMapping("/all/finish/{id}")
    public ResponseEntity<String> create(@PathVariable("id") String id) {
        transferService.finishTransfer(id);
        return ResponseEntity.ok("SUCCESSFULLY");
    }

    @GetMapping("/any/getById/{id}")
    public ResponseEntity<TransferDTO> getById(@PathVariable("id") String id) {
        TransferDTO response = transferService.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestBody TransferDTO dto,
                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<TransferDTO> list = transferService.pagination(page, size, dto);
        return ResponseEntity.ok().body(list);
    }
}
