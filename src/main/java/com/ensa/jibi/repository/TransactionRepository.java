package com.ensa.jibi.repository;

import com.ensa.jibi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByIdClient(Long idClient);

    List<Transaction> getTransactionsByIdClient(Long clientId);
}
