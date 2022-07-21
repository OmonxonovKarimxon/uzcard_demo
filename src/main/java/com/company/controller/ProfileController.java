package com.company.controller;

import com.company.dto.company.CompanyUpdateDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.dto.profile.ProfileUpdateDTO;
import com.company.dto.profile.RegistretionProfileDTO;
import com.company.service.ProfileService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid RegistretionProfileDTO dto){
       String response = profileService.create(dto);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody ProfileUpdateDTO dto){
        String response = profileService.update(dto);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getShortInfoByCategoryKey(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "5") int size) {

        PageImpl<ResponseCompanyDTO> list = profileService.pagination(page, size);
        return ResponseEntity.ok().body(list);
    }

}
