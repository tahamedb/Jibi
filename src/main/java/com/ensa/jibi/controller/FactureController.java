package com.ensa.jibi.controller;

import com.ensa.jibi.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "http://localhost:4200/")

public class FactureController {

    @Autowired
    private FactureService factureService;

    @PostMapping("/payer")
    public ResponseEntity<String> payerFacture(@RequestParam Long factureId, @RequestParam Long clientId) {
        try {
            factureService.PayerFacture(factureId, clientId);
            return ResponseEntity.ok("Facture paid successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}
