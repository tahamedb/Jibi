package com.ensa.jibi.model;

import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String beneficaire;
}
