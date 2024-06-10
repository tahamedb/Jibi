package com.ensa.jibi.repository;

import com.ensa.jibi.model.BackOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackOfficeRepository  extends JpaRepository<BackOffice,Long> {
    Optional<BackOffice> findByUsername(String username);
    BackOffice findByEmail(String email);
    boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}
