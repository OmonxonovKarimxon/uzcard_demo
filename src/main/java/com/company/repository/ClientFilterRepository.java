package com.company.repository;

import com.company.dto.client.ClientResponseDTO;
import com.company.entity.ClientEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientFilterRepository   {

    private final EntityManager entityManager;

    public ClientFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ClientEntity> filter(ClientResponseDTO dto, Pageable pageable) {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM ClientEntity a ");
        builder.append(" where visible = true  ");

        if (dto.getId() != null) {
            builder.append(" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getStatus() != null) {
            builder.append(" and a.status = '" + dto.getStatus() + "' ");
        }

        if (dto.getPassportNumber() != null) {
            builder.append(" and a.passportNumber = '" +dto.getPassportNumber() + "' ");
        }

        if (dto.getPassportSeria() != null) {
            builder.append(" and a.passportSeria='" + dto.getPassportSeria() + "' ");
        }

        if (dto.getPhone() != null) {
            builder.append(" and a.phone='" + dto.getPhone() + "'");
        }
        if (dto.getName() != null) {
            builder.append(" and a.name='" + dto.getName() + "'");
        }
        if (dto.getSurname() != null) {
            builder.append(" and a.surname='" + dto.getSurname() + "'");
        }
        if (dto.getMiddleName() != null) {
            builder.append(" and a.middleName='" + dto.getMiddleName() + "'");
        }


        Query query = entityManager.createQuery(builder.toString());
        List<ClientEntity> profileList = query.getResultList();

        return profileList;
    }

}
