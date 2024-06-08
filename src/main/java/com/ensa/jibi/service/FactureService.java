package com.ensa.jibi.service;

import com.ensa.jibi.cmi.CmiService;
import com.ensa.jibi.model.*;
import com.ensa.jibi.repository.ClientRepository;
import com.ensa.jibi.repository.FactureRepository;
import com.ensa.jibi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

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

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture saveFacture(Facture facture) {
        return factureRepository.save(facture);
    }

    public Facture toggleImpaye(Long factureId, Long clientId) {
        Optional<Facture> f = factureRepository.findById(factureId);
        f.ifPresent(facture -> {
            facture.setStatusfacture(Statusfacture.PAYE);
            facture.setDatepaiement(LocalDateTime.now());
            facture.setClientid(clientId);
        });
        return f.map(factureRepository::save).orElse(null);
    }

    public void deleteFactureById(Long id) {
        factureRepository.deleteById(id);
    }

    public void PayerFacture(Long factureId, Long clientId) {
        Optional<Facture> fOpt = factureRepository.findById(factureId);
        Optional<Client> cOpt = clientRepository.findById(clientId);

        if (fOpt.isPresent() && cOpt.isPresent()) {
            Facture facture = fOpt.get();
            Client client = cOpt.get();
            Double montant = facture.getMontant();

            if (transactionService.isBelowLimit(montant, client)) {
                System.out.println("ana te7t limit mzyan"+cmiService.isSoldeSuffisant(clientId, montant) );
                if (cmiService.isSoldeSuffisant(clientId, montant)) {
                    toggleImpaye(factureId,clientId);

                    // Create a new transaction and save it
                    Transaction transaction = new Transaction();
                    transaction.setIdClient((client.getId()));
                    transaction.setMontant(montant);
                    transaction.setType(TransactionType.SORTIE);
                    LocalDateTime currentDate = LocalDateTime.now();
                    transaction.setDate(currentDate);
                    String nomCreancier=facture.getCreance().getCreancier().getName();
                    transaction.setBeneficaire(nomCreancier);
                    transaction.setLibelle("Paiement de facture"+facture.getFactref()+"de valeur"+facture.getMontant()+" MAD");
                    transactionRepository.save(transaction);
                } else {
                    throw new RuntimeException("Insufficient balance for client ID: " + clientId);
                }
            } else {
                throw new RuntimeException("Transaction amount exceeds limit for client ID: " + clientId);
            }
        } else {
            throw new IllegalArgumentException("Facture or Client not found for IDs: " + factureId + ", " + clientId);
        }
    }


    public List<Facture> getImpayeFacturesByRefAndCreance(String factref, Long creance) {
        return factureRepository.findByStatusfactureAndFactrefAndCreance_Id(Statusfacture.IMPAYE, factref, creance);
    }
}
