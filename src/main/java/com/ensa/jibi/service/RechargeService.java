package com.ensa.jibi.service;

import com.ensa.jibi.cmi.CmiService;
import com.ensa.jibi.model.*;
import com.ensa.jibi.repository.ClientRepository;
import com.ensa.jibi.repository.RechargeRepository;
import com.ensa.jibi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RechargeService {

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CmiService cmiService;

    public Recharge saveRecharge(Recharge recharge) {
        return rechargeRepository.save(recharge);
    }

    public void PayerRecharge(Long rechargeId, Long clientId) {
        Optional<Recharge> rOpt = rechargeRepository.findById(rechargeId);
        Optional<Client> cOpt = clientRepository.findById(clientId);

        if (rOpt.isPresent() && cOpt.isPresent()) {
            Recharge recharge = rOpt.get();
            Client client = cOpt.get();
            Double montant = recharge.getMontant();
            RechargeType rechargeType = recharge.getTyperecharge();

            if (isBelowLimit(montant, client)) {
                if (cmiService.isSoldeSuffisant(clientId, montant)) {
                    toggleImpaye(rechargeId);

                    // Create a new transaction and save it
                    Transaction transaction = new Transaction();
                    transaction.setIdClient(client.getId());
                    transaction.setMontant(montant);
                    transaction.setType(TransactionType.SORTIE);
                    LocalDateTime currentDate = LocalDateTime.now();
                    transaction.setDate(currentDate);
                    String nomCreancier = recharge.getCreance().getCreancier().getName();
                    transaction.setBeneficaire(nomCreancier);

                    // Utilisation du type de recharge dans la description
                    transaction.setLibelle("Paiement de recharge " + rechargeType.toString().toLowerCase() + " pour le numéro: " + recharge.getPhonenumber() + " de valeur: " + recharge.getMontant() + " MAD");
                    transactionRepository.save(transaction);
                } else {
                    throw new RuntimeException("Insufficient balance for client ID: " + clientId);
                }
            } else {
                throw new RuntimeException("Transaction amount exceeds limit for client ID: " + clientId);
            }
        } else {
            throw new IllegalArgumentException("Recharge or Client not found for IDs: " + rechargeId + ", " + clientId);
        }
    }

    public boolean isBelowLimit(Double montant, Client client) {
        Double limit = client.getAccountType().getAccountLimit();
        return transactionService.getTodayMontantSum(client.getId()) + montant < limit;
    }

    public void toggleImpaye(Long rechargeId) {
        Optional<Recharge> r = rechargeRepository.findById(rechargeId);
        r.ifPresent(recharge -> {
            recharge.setStatusRecharge(StatusRecharge.PAYE);
            recharge.setDatepaiement(LocalDateTime.now());
        });
        r.map(rechargeRepository::save).orElse(null);
    }
}
