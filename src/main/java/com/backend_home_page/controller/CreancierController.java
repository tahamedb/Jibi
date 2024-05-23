package com.backend_home_page.controller;

import com.backend_home_page.dtos.CreancierDTO;
import com.backend_home_page.entities.CreanceType;
import com.backend_home_page.entities.Creancier;
import com.backend_home_page.services.CreancierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/creanciers")
public class CreancierController {

    @Autowired
    private CreancierService creancierService;

    // Endpoint to get a Creancier by ID
    @GetMapping("/id")
    public ResponseEntity<Creancier> getCreancierById(@RequestParam(value = "id", required = false) Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Creancier> creancierOptional = creancierService.getCreancierById(id);
        if (creancierOptional.isPresent()) {
            return new ResponseEntity<>(creancierOptional.get(), HttpStatus.OK);
        } else {
            System.out.println("no such id");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all Creanciers
    @GetMapping
    public ResponseEntity<List<CreancierDTO>> getAllCreanciers() {
        List<Creancier> creanciers = creancierService.getAllCreanciers();
        List<CreancierDTO> creancierDTOs = creanciers.stream()
                .map(CreancierDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(creancierDTOs);
    }

}
