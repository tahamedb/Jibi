package com.backend_home_page.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Creance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private CreanceType creancetype;

    private String formFieldsJSON; // JSON string representing the form fields

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creancier_id") // Assuming the foreign key column in Creance table is creancier_id
    private Creancier creancier;

}