package com.company.service;

import com.company.dto.company.CompanyUpdateDTO;
import com.company.dto.company.RegistrationCompanyDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.entity.CompanyEntity;
import com.company.exps.ItemNotFoundException;
import com.company.repository.CompanyRepository;
import com.company.util.MD5Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public String create(RegistrationCompanyDTO dto) {
        CompanyEntity entity = new CompanyEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setAddress(dto.getAddress());
        entity.setContact(dto.getContact());
        entity.setName(dto.getName());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        companyRepository.save(entity);
        return "successfully";

    }

    public String update(CompanyUpdateDTO dto) {
        Optional<CompanyEntity> byId = companyRepository.findById(dto.getId());
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("itam not fount exception");
        }
        String password = MD5Util.getMd5(dto.getPassword());
        companyRepository.update(password, dto.getAddress(), dto.getContact(), dto.getName(), dto.getId());
        return "successfully";
    }

    public PageImpl<ResponseCompanyDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyEntity> companyEntityPage = companyRepository.pagination(pageable);
        List<ResponseCompanyDTO> dtoList = new LinkedList<>();
        companyEntityPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl(dtoList, pageable, companyEntityPage.getTotalElements());
    }


    private ResponseCompanyDTO entityToDTO(CompanyEntity entity) {
        ResponseCompanyDTO dto = new ResponseCompanyDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setContact(entity.getContact());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public CompanyEntity get(String id) {
        return companyRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("company not found");
        });
    }
    public CompanyEntity getByUsername(String username) {
        return companyRepository.findByUsername(username).orElseThrow(() -> {
            throw new ItemNotFoundException("company not found");
        });
    }

}
