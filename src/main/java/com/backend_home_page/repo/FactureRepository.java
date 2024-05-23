package com.backend_home_page.repo;

import com.backend_home_page.entities.Facture;
import com.backend_home_page.entities.Statusfacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByStatusfactureAndFactrefAndCreance_Id(Statusfacture statusfacture, String factref, Long creanceId);
}
