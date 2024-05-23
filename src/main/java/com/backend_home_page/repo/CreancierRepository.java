package com.backend_home_page.repo;

import com.backend_home_page.entities.Creancier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreancierRepository extends JpaRepository<Creancier, Long> {
    // You can add custom query methods here if needed
}
