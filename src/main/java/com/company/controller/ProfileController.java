package com.company.controller;

import com.company.dto.company.RegistrationCompanyDTO;
import com.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody RegistrationCompanyDTO dto){
       String response = companyService.create(dto);
        return ResponseEntity.ok().body(response);
    }

}
