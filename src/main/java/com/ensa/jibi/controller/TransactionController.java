package com.ensa.jibi.controller;

import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping()
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/{clientId}")
    public List<Transaction> getTransactionsByClientId(@PathVariable Long clientId) {
        return transactionService.getTransactionsByClientId(clientId);
    }

    @PostMapping("/transfert")
    public ResponseEntity<String> payerFacture(@RequestParam double montant, @RequestParam Long senderId, @RequestParam Long recieverId ) {
        try {
            if(senderId == recieverId){
                throw new IllegalArgumentException("Error : Sender is the sanme as the Reciever");
            } else if(montant == 0){
                throw new IllegalArgumentException("Error : Montant null");
            }
            transactionService.effectuerTransfert(montant, senderId,recieverId);
            return ResponseEntity.ok("Transfert done successfully."+montant);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

}
