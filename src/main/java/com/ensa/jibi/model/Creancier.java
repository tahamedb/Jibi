package com.ensa.jibi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Creancier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CreancierType categorieCreancier;

    private String name;

    private String logo;

    @OneToMany(mappedBy = "creancier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Creance> creances = new ArrayList<>();

    public void addCreance(Creance creance) {
        creances.add(creance);
        creance.setCreancier(this);
    }

    public void removeCreance(Creance creance) {
        creances.remove(creance);
        creance.setCreancier(null);
    }


}