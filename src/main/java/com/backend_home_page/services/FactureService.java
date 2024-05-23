package com.backend_home_page.services;

import com.backend_home_page.entities.Facture;
import com.backend_home_page.entities.Statusfacture;
import com.backend_home_page.repo.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture saveOrUpdateFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public void deleteFactureById(Long id) {
        factureRepository.deleteById(id);
    }

    public List<Facture> getImpayeFacturesByRefAndCreance(String factref, Long creance) {
        return factureRepository.findByStatusfactureAndFactrefAndCreance_Id(Statusfacture.IMPAYE, factref, creance);
    }
}
