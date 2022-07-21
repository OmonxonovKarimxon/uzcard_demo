package com.company.service;

import com.company.dto.company.CompanyUpdateDTO;
import com.company.dto.company.ResponseCompanyDTO;
import com.company.dto.profile.ProfileUpdateDTO;
import com.company.dto.profile.RegistretionProfileDTO;
import com.company.dto.profile.ResponseProfileDTO;
import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.exps.ItemNotFoundEseption;
import com.company.repository.ProfileRepository;
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
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String create(RegistretionProfileDTO dto) {

        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setName(dto.getName());
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        entity.setSurname(dto.getSurname());
        profileRepository.save(entity);

        return "successfully";

    }
    public String update(ProfileUpdateDTO dto) {
        Optional<ProfileEntity> byId = profileRepository.findById(dto.getId());
        if(byId.isEmpty()){
            throw  new ItemNotFoundEseption("itam not fount exception");
        }
      String password=  MD5Util.getMd5(dto.getPassword());
        profileRepository.update(password,dto.getName(),dto.getSurname(), dto.getId());
        return "successfully";
    }

    public PageImpl<ResponseCompanyDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> videoShortInfoPage = profileRepository.pagination(pageable);
        List<ResponseProfileDTO> dtoList = new LinkedList<>();
        videoShortInfoPage.getContent().forEach(entity -> {
            dtoList.add(entityToDTO(entity));
        });
        return new PageImpl(dtoList, pageable, videoShortInfoPage.getTotalElements());
    }



    private ResponseProfileDTO entityToDTO(ProfileEntity entity){
        ResponseProfileDTO dto = new ResponseProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setStatus(entity.getStatus());
        dto.setUsername(entity.getUsername());

        return dto;
    }
}
