package com.company.service;

import com.company.dto.client.ClientRegisterDTO;
import com.company.dto.client.ClientResponseDTO;
import com.company.dto.client.ClientUpdateDTO;
import com.company.entity.ClientEntity;
import com.company.exps.ItemNotFoundException;
import com.company.repository.ClientFilterRepository;
import com.company.repository.ClientRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientFilterRepository clientFilterRepository;

    public ClientService(ClientRepository clientRepository, ClientFilterRepository clientFilterRepository) {
        this.clientRepository = clientRepository;
        this.clientFilterRepository = clientFilterRepository;
    }


    public String create(ClientRegisterDTO dto) {

        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setPassportSeria(dto.getPassportSeria());
        entity.setStatus(dto.getStatus());
        entity.setPhone(dto.getPhone());
        clientRepository.save(entity);
        return "successfully";

    }

    public String update(ClientUpdateDTO dto) {
        Optional<ClientEntity> byId = clientRepository.findById(dto.getId());
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("itam not fount exception");
        }

        clientRepository.update(dto.getName(), dto.getSurname(), dto.getId());
        return "successfully";
    }

    public PageImpl<ClientResponseDTO> pagination(int page, int size, ClientResponseDTO dto) {
        Pageable pageable = PageRequest.of(page, size);
        List<ClientEntity> filter = clientFilterRepository.filter(dto, pageable);
        List<ClientResponseDTO> dtoList = new ArrayList<>();
        filter.forEach(clientEntity -> {
            dtoList.add(entityToDTO(clientEntity));
        });
        return new PageImpl(dtoList, pageable, filter.size());
    }




    private ClientResponseDTO entityToDTO(ClientEntity entity) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setMiddleName(entity.getMiddleName());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setPassportNumber(entity.getPassportNumber());
        dto.setPassportSeria(entity.getPassportSeria());
        return dto;
    }
}
