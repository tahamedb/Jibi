package com.ensa.jibi.service;


import com.ensa.jibi.dto.CreanceFormDTO;
import com.ensa.jibi.model.Creance;

import com.ensa.jibi.model.Creancier;
import com.ensa.jibi.repository.CreanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CreanceService{

    @Autowired
    private CreanceRepository creanceRepository;

    public List<Creance> getAllCreances() {
        return creanceRepository.findAll();
    }

    public Optional<Creance> getCreanceById(Long id) {
        return creanceRepository.findById(id);
    }


    public void deleteCreanceById(Long id) {
        creanceRepository.deleteById(id);
    }



}
