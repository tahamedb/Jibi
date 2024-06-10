package com.ensa.jibi.service;


import com.ensa.jibi.model.Creancier;
import com.ensa.jibi.repository.CreancierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreancierService {

    private final CreancierRepository creancierRepository;


    @Autowired
    public CreancierService(CreancierRepository creancierRepository) {
        this.creancierRepository = creancierRepository;
    }
    public List<Creancier> getAllCreanciers() {
        return creancierRepository.findAll();
    }

    public Optional<Creancier> getCreancierById(Long id) {
        return creancierRepository.findById(id);
    }

    public Creancier saveOrUpdateCreancier(Creancier creancier) {
        return creancierRepository.save(creancier);
    }

    public void deleteCreancierById(Long id) {
        creancierRepository.deleteById(id);
    }


}
