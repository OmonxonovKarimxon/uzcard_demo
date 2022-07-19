package com.company.service;

import com.company.dto.company.RegistrationCompanyDTO;
import com.company.entity.CompanyEntity;
import com.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

  public String create(RegistrationCompanyDTO dto) {
    CompanyEntity entity =new CompanyEntity();
    entity.setUsername(dto.getUsername());
    entity.setPassword(dto.getPassword());
    entity.setAddress(dto.getAddress());
    entity.setContact(dto.getContact());
    entity.setName(dto.getName());
    entity.setRole(dto.getRole());
    entity.setStatus(dto.getStatus());
    companyRepository.save(entity);
    return "successfully";

  }
}
