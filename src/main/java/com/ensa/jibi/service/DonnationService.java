package com.ensa.jibi.service;

import com.ensa.jibi.cmi.CmiService;
import com.ensa.jibi.model.Creance;
import com.ensa.jibi.model.Donnation;
import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.model.TransactionType;
import com.ensa.jibi.repository.DonnationRepository;
import com.ensa.jibi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DonnationService {

    @Autowired
    private DonnationRepository donnationRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CmiService cmiService;

    public Donnation makeDonnation(String donator, double amount, Creance creance,Long clientId) {
        Donnation donnation = new Donnation();
        donnation.setDonator(donator);
        donnation.setAmount(amount);
        donnation.setCreance(creance);

        // Vérifier si le montant est suffisant
        if (cmiService.isSoldeSuffisant(clientId, amount)) {
            // Enregistrer la donation
            donnationRepository.save(donnation);

            // Créer une transaction associée
            createTransaction(donnation);

            return donnation;
        } else {
            throw new RuntimeException("Insufficient balance for creancier ID: " + creance.getCreancier().getId());
        }
    }

    private void createTransaction(Donnation donnation) {
        // Créer une nouvelle transaction
        Transaction transaction = new Transaction();
        transaction.setIdClient(donnation.getCreance().getCreancier().getId());  // ID du créancier (association)
        transaction.setMontant(donnation.getAmount());
        transaction.setType(TransactionType.SORTIE);  // La transaction est une sortie pour le client
        transaction.setDate(LocalDateTime.now());
        transaction.setBeneficaire(donnation.getCreance().getCreancier().getName());  // Nom de l'association bénéficiaire
        transaction.setLibelle("Donation de " + donnation.getAmount() + " MAD à " + donnation.getCreance().getCreancier().getName());

        // Enregistrer la transaction
        transactionRepository.save(transaction);
    }

}
