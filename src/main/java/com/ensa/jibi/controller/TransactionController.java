package com.ensa.jibi.controller;

import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
