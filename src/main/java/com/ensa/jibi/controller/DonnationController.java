package com.ensa.jibi.controller;


import com.ensa.jibi.model.Creance;
import com.ensa.jibi.model.Donnation;
import com.ensa.jibi.service.CreanceService;
import com.ensa.jibi.service.DonnationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/donnations")
public class DonnationController {

    @Autowired
    private DonnationService donnationService;

    @Autowired
    private CreanceService creanceService;

    @PostMapping("/make")
    public ResponseEntity<String> makeDonnation(
            @RequestParam String donator,
            @RequestParam double amount,
            @RequestParam Long creanceId,
            @RequestParam Long clientId
    ) {
        try {
            Optional<Creance> creance = creanceService.getCreanceById(creanceId);
            if (creance == null) {
                throw new IllegalArgumentException("Creance not found for ID: " + creanceId);
            }
            donnationService.makeDonnation(donator, amount, creance.get(),clientId);
            return ResponseEntity.ok("Donnation made successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }


}

