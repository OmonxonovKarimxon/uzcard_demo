package com.company.controller;

import com.company.dto.company.CompanyUpdateDTO;
import com.company.dto.company.RegistrationCompanyDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.service.CompanyService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody CompanyUpdateDTO dto){
        String response = companyService.update(dto);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getShortInfoByCategoryKey(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<ResponseCompanyDTO> list = companyService.pagination(page, size);
        return ResponseEntity.ok().body(list);
    }

}
