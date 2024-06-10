package com.ensa.jibi.service;

import com.ensa.jibi.model.Agence;
import com.ensa.jibi.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgenceService {
    @Autowired
    public AgenceRepository agenceRepository;

    public  void addAgence(Agence agence){
        agenceRepository.save(agence);
    }

}
