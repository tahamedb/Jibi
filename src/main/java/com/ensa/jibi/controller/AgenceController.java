package com.ensa.jibi.controller;


import com.ensa.jibi.model.Agence;
import com.ensa.jibi.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agences")
@CrossOrigin(origins = "http://localhost:4200")
public class AgenceController {
    @Autowired
    AgenceService agenceService;
    @PostMapping("/add")
    public ResponseEntity<String> addAgence(@RequestBody Agence agence) {
        agenceService.addAgence(agence);
        return new ResponseEntity<>("Agence ajoutée avec succès", HttpStatus.CREATED);
    }
}
