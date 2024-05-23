package com.backend_home_page.controller;

import com.backend_home_page.entities.Transaction;
import com.backend_home_page.services.TransactionService;
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
