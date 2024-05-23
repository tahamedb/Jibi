package com.ensa.jibi.service;

import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository TransactionRepository;


    @Autowired
    public TransactionService(TransactionRepository TransactionRepository) {
        this.TransactionRepository = TransactionRepository;
    }
    public List<Transaction> getAllTransactions() {
        return TransactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return TransactionRepository.findById(id);
    }

    public Transaction saveOrUpdateTransaction(Transaction Transaction) {
        return TransactionRepository.save(Transaction);
    }

    public void deleteTransactionById(Long id) {
        TransactionRepository.deleteById(id);
    }


    public List<Transaction> getTransactionsByClientId(Long clientId) {
        return TransactionRepository.getTransactionsByIdClient(clientId);
    }
}
