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

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update ProfileEntity  set password=?1,name=?2,surname=?3 where id=?4 ")
    void update(String password, String name, String surname, String id);

    @Query("from ProfileEntity ")
    Page<ProfileEntity> pagination(Pageable pageable);
}
