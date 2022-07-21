package com.company.repository;

import com.company.entity.ClientEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, String> {
    @Transactional
    @Modifying
    @Query("update ClientEntity  set name=?1, surname=?2 where id=?3")
    void update(String name, String surname, String id);


}
