package com.company.repository;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE  CompanyEntity  set  password=?1, address=?2,contact=?3,name=?4 where id=?5")
    void update(String password, String address, String contact, String name, String id);

    @Query("from CompanyEntity ")
    Page<CompanyEntity> pagination(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update CompanyEntity set balance = ?1 where id = ?2")
    void changeBalance(Double balance, String id);
}
