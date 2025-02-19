package com.ensa.jibi.repository;

import com.ensa.jibi.model.Creance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {
    // You can add custom query methods here if needed
}
