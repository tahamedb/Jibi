package com.ensa.jibi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Recharge  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private double montant;
    @Enumerated(EnumType.STRING)
    private RechargeType typerecharge;

    private String phonenumber;
    @Enumerated(EnumType.STRING)
    private StatusRecharge statusRecharge;

    private LocalDateTime datepaiement;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CreanceID", nullable = false)
    private Creance creance;
}
