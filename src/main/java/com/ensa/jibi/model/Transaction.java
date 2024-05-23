package com.ensa.jibi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idClient;

    private String libelle;

    private LocalDateTime date;

    private Double montant;

    private TransactionType type; // Either "entrée" or "sortie" in cap letter lmao

    private String beneficaire;
    // Add getters and setters
}
