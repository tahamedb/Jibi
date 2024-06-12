package com.ensa.jibi.repository;

import com.ensa.jibi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByIdClient(Long idClient);

    List<Transaction> getTransactionsByIdClient(Long clientId);
    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.idClient=:idClient AND t.date >= :startOfDay AND t.date < :endOfDay")
    Long findSumOfAllTransactionsToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay,@Param("idClient") Long idClient);
}
