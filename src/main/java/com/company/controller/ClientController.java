package com.company.controller;

import com.company.dto.client.ClientRegisterDTO;
import com.company.dto.client.ClientResponseDTO;
import com.company.dto.client.ClientUpdateDTO;
import com.company.service.ClientService;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping("/bank/create")
    public ResponseEntity<?> create(@RequestBody @Valid ClientRegisterDTO dto){
       String response = clientService.create(dto);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/bank/update")
    public ResponseEntity<?> update(@RequestBody ClientUpdateDTO dto){
        String response = clientService.update(dto);
        return ResponseEntity.ok().body(response);
    }

        @GetMapping("/adm/pagination")
    public ResponseEntity<?> getShortInfoByCategoryKey(@RequestBody ClientResponseDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<ClientResponseDTO> list = clientService.pagination(page, size, dto);
        return ResponseEntity.ok().body(list);
    }


}
