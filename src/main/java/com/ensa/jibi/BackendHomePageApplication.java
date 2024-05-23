package com.ensa.jibi;


import com.ensa.jibi.service.TransactionService;
import com.ensa.jibi.model.Creance;
import com.ensa.jibi.model.Creancier;
import com.ensa.jibi.model.Transaction;
import com.ensa.jibi.model.TransactionType;
import com.ensa.jibi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BackendHomePageApplication implements CommandLineRunner {

    @Autowired
    private CreanceRepository creanceRepository;

    @Autowired
    private CreancierRepository creancierRepository;

    @Autowired
    private DonnationRepository donnationRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private RechargeRepository rechargeRepository;

    @Autowired
    private TransactionService transactionService;

    private void generateMockTransactions(int n) {
        List<Creance> creances = creanceRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            Transaction transaction = new Transaction();
            transaction.setIdClient(123L); // For testing purposes, using a fixed idClient
            transaction.setDate(LocalDateTime.now());
            transaction.setMontant(random.nextDouble() * 1000); // Random amount between 0 and 1000

            // Randomly select a creance
            Creance creance = creances.get(random.nextInt(creances.size()));

            // Generate libelle using creancier and creance name
            String libelle = String.format("%s - %s", creance.getCreancier().getName(), creance.getName());
            transaction.setLibelle(libelle);

            // Set transaction type
            transaction.setType(random.nextBoolean() ? TransactionType.ENTREE : TransactionType.SORTIE);

            // Set beneficiary as one of the creancier names
            List<Creancier> creanciers = creancierRepository.findAll();
            Creancier beneficiary = creanciers.get(random.nextInt(creanciers.size()));
            transaction.setBeneficaire(beneficiary.getName());

            // Save the transaction
            transactionService.saveOrUpdateTransaction(transaction);
        }
    }


    public  static void main(String[] args) {
        SpringApplication.run(BackendHomePageApplication.class, args);
    }
    @Override public void run(String... args) throws Exception {

//        Creancier creancier1 = new Creancier(null, CreancierType.OPERATEUR, "IAM", "https://upload.wikimedia.org/wikipedia/fr/6/6e/Maroc_telecom_logo.svg?download", new ArrayList<>());
//        Creancier creancier2 = new Creancier(null, CreancierType.UTILITY, "RADEMA", "https://www.infomediaire.net/wp-content/uploads/2021/01/RADEEMA-Emploi-Recrutement-Dreamjob.ma_.png", new ArrayList<>());
//        Creancier creancier3 = new Creancier(null, CreancierType.ASSOCIATION, "AME", "https://www.sosaction.org/wp-content/uploads/2019/04/LOGO-AME-1-659x396.png", new ArrayList<>());
//        creancierRepository.saveAll(Arrays.asList(creancier1, creancier2,creancier3));
//
//        // Create Creances for Creancier1
//        Creance creance1_1 = new Creance(null, "Recharge Mobile", CreanceType.RECHARGE, "{\"field\":\"value\"}", creancier1);
//        Creance creance1_2 = new Creance(null, "Donnation GAZA", CreanceType.DONNATION, "{\"field\":\"value\"}", creancier1);
//        Creance creance3_1 = new Creance(null, "Donnation Youth", CreanceType.DONNATION, "{\"field\":\"value\"}", creancier3);
//        creanceRepository.saveAll(Arrays.asList(creance1_1, creance1_2));
//
//        // Create Recharge linked to Creance1_1
//        Recharge recharge = new Recharge();
//        recharge.setMontant(50.0);
//        recharge.setTyperecharge(RechargeType.INTERNET);
//        recharge.setPhonenumber("1234567890");
//        recharge.setCreance(creance1_1);
//        rechargeRepository.save(recharge);
//
//        // Create Donnation linked to Creance1_2
//        Donnation donnation = new Donnation();
//        donnation.setDonator("Donator One");
//        donnation.setAmount(100.0);
//        donnation.setCreance(creance1_2);
//        donnationRepository.save(donnation);
//
//        // Create Creances for Creancier2
//        Creance creance2_1 = new Creance(null, "Electricity Bill", CreanceType.FACTURE, null, creancier2);
//        Creance creance2_2 = new Creance(null, "Water Bill", CreanceType.FACTURE, null, creancier2);
//        creanceRepository.saveAll(Arrays.asList(creance2_1, creance2_2));
//
//        // Create two Factures for Creance2_1 with different months
//        Facture facture1 = new Facture();
//        facture1.setFactref("E12345");
//        facture1.setMonth(1);
//        facture1.setEcheance(LocalDateTime.now().plusDays(30));
//        facture1.setClientid("Client123");
//        facture1.setDatepaiement(LocalDateTime.now());
//        facture1.setStatusfacture(Statusfacture.PAYE);
//        facture1.setCreance(creance2_1);
//
//        Facture facture2 = new Facture();
//        facture2.setFactref("E12345");
//        facture2.setMonth(2);
//        facture2.setEcheance(LocalDateTime.now().plusDays(60));
//        facture2.setStatusfacture(Statusfacture.PAYE);
//        facture2.setCreance(creance2_1);
//
//        factureRepository.saveAll(Arrays.asList(facture1, facture2));
//
//        // Create two Factures for Creance2_2 with different months
//        Facture facture3 = new Facture();
//        facture3.setFactref("W12345");
//        facture3.setMonth(1);
//        facture3.setEcheance(LocalDateTime.now().plusDays(30));
//        facture3.setStatusfacture(Statusfacture.IMPAYE);
//        facture3.setCreance(creance2_2);
//
//        Facture facture4 = new Facture();
//        facture4.setFactref("W12345");
//        facture4.setMonth(2);
//        facture4.setEcheance(LocalDateTime.now().plusDays(60));
//        facture4.setStatusfacture(Statusfacture.IMPAYE);
//        facture4.setCreance(creance2_2);
//
//        factureRepository.saveAll(Arrays.asList(facture3, facture4));
//
//        // Link Creances to Creanciers
//        creancier1.addCreance(creance1_1);
//        creancier1.addCreance(creance1_2);
//        creancier2.addCreance(creance2_1);
//        creancier2.addCreance(creance2_2);
//        creancierRepository.saveAll(Arrays.asList(creancier1, creancier2));
//        // save into da database
//        generateMockTransactions(10)

            }
        }
