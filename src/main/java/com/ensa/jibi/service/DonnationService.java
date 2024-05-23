package com.ensa.jibi.service;

import com.ensa.jibi.model.Donnation;
import com.ensa.jibi.repository.DonnationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonnationService {

    @Autowired
    private DonnationRepository donnationRepository;

    public List<Donnation> getAllDonnations() {
        return donnationRepository.findAll();
    }

    public Optional<Donnation> getDonnationById(Long id) {
        return donnationRepository.findById(id);
    }

    public Donnation saveOrUpdateDonnation(Donnation donnation) {
        return donnationRepository.save(donnation);
    }

    public void deleteDonnationById(Long id) {
        donnationRepository.deleteById(id);
    }
}
