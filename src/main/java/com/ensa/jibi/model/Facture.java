package com.ensa.jibi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String factref;

    private int month;

    private LocalDateTime echeance;

    private String clientid;

    private LocalDateTime datepaiement;

    private Statusfacture statusfacture;

    private double montant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CreanceID", nullable = false)
    private Creance creance;



}
