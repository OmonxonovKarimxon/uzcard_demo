package com.company.repository;

import com.company.dto.client.ClientResponseDTO;
import com.company.dto.transfer.TransferDTO;
import com.company.entity.ClientEntity;
import com.company.entity.TransferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TransferFilterRepository {

    private final EntityManager entityManager;

    public TransferFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<TransferEntity> filter(TransferDTO dto, Pageable pageable) {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM TransferEntity a ");
        builder.append(" where visible = true  ");

        String addition = "";

        if (dto.getFromCard() != null) {
            addition += (" and a.fromCard = '" + dto.getFromCard() + "' ");
        }

        if (dto.getToCard() != null) {
            addition += (" and a.toCard = '" + dto.getToCard() + "' ");
        }

        if (dto.getTotalAmount() != null) {
            addition +=(" and a.totalAmount='" + dto.getTotalAmount() + "' ");
        }
        builder.append(addition);

        Query query = entityManager.createQuery(builder.toString());

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List <TransferEntity> entityListList = query.getResultList();

        String builder1 = " SELECT count(a) FROM TransferEntity a " +
                " where a.visible = true  " + addition;

        Query queryTotal = entityManager.createQuery(builder1);
        long countResult = (long)queryTotal.getSingleResult();
        int totalElement=(int)countResult;
        return new PageImpl<>(entityListList, pageable, totalElement);
    }

}
