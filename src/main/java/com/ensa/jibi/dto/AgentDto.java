package com.ensa.jibi.dto;


import com.ensa.jibi.model.Agent;
import com.ensa.jibi.model.PieceIdentite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDto {
    private String nom;
    private  String prenom;
    private PieceIdentite pieceIdentite;
    private Long numeroIdentite;
    private String adresse;
    private String email;
    private String numeroTelephone;
    private String immatriculation;
    private String description;
    private String patente;
    private LocalDate dateofbirth;
    private String nomFichier;
    public AgentDto(Agent agent){
        this.nom= agent.getNom();
        this.prenom= agent.getPrenom();
        this.adresse= agent.getAdresse();
        this.dateofbirth=agent.getDateofbirth();
        this.email= agent.getEmail();
        this.description=agent.getDescription();
        this.immatriculation= agent.getImmatriculation();
        this.nomFichier=agent.getNomFichier();
       this.numeroIdentite= Long.valueOf(agent.getNumeroIdentite());
        this.numeroTelephone=agent.getNumeroTelephone();
        this.patente=agent.getPatente();
        this.pieceIdentite=agent.getPieceIdentite();



    }
    // Converts AgentDto to Agent entity
    public static Agent toEntity(AgentDto agentDto) {
        if (agentDto == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setNom(agentDto.getNom());
        agent.setPrenom(agentDto.getPrenom());
        agent.setPieceIdentite(agentDto.getPieceIdentite());
        agent.setNumeroIdentite(String.valueOf(agentDto.getNumeroIdentite()));
        agent.setAdresse(agentDto.getAdresse());
        agent.setEmail(agentDto.getEmail());
        agent.setNumeroTelephone(agentDto.getNumeroTelephone());
        agent.setImmatriculation(agentDto.getImmatriculation());
        agent.setDescription(agentDto.getDescription());
        agent.setPatente(agentDto.getPatente());
        agent.setDateofbirth(agentDto.getDateofbirth());
        agent.setNomFichier(agentDto.getNomFichier());
        return agent;
    }
}
