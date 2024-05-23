package com.ensa.jibi.service;

import com.ensa.jibi.dto.CreanceFormDTO;
import com.ensa.jibi.model.Creance;
import com.ensa.jibi.model.Creancier;
import com.ensa.jibi.repository.CreanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreanceService{

    @Autowired
    private CreanceRepository creanceRepository;

    public List<Creance> getAllCreances() {
        return creanceRepository.findAll();
    }

    public Optional<Creance> getCreanceById(Long id) {
        return creanceRepository.findById(id);
    }

    public Creance saveOrUpdateCreance(Creance creance) {
        return creanceRepository.save(creance);
    }

    public void deleteCreanceById(Long id) {
        creanceRepository.deleteById(id);
    }


    public CreanceFormDTO getCreanceFormDetails(Long id) {

        Optional<Creance> creanceOptional = creanceRepository.findById(id);

        if (creanceOptional.isPresent()) {
            Creance creance = creanceOptional.get();

            Creancier creancier = creance.getCreancier();

            String formFieldsJSON;
            switch (creance.getCreancetype()) {

                case FACTURE:
                    formFieldsJSON = "[{\"key\":\"ReferenceID\",\"label\":\"Reference ID\",\"type\":\"text\",\"placeholder\":\"Enter Reference ID\"}]";
                    break;
                case DONNATION:
                    formFieldsJSON = "[{\"key\":\"donorName\",\"label\":\"Nom et prénom du donnateur\",\"type\":\"text\",\"placeholder\":\"Enter donor name\"}," +
                            "{\"key\":\"amount\",\"label\":\"Montant du don\",\"type\":\"number\",\"placeholder\":\"Enter donation amount\"}]";
                    break;
                case RECHARGE:
                    formFieldsJSON = "[{\"key\":\"phoneNumber\",\"label\":\"Numero de telephone\",\"type\":\"text\",\"placeholder\":\"Enter phone number\"}," +
                            "{\"key\":\"rechargeType\",\"label\":\"Type de recharge\",\"type\":\"text\",\"placeholder\":\"Enter recharge type\"}," +
                            "{\"key\":\"amount\",\"label\":\"Montant de recharge\",\"type\":\"number\",\"placeholder\":\"Enter recharge amount\"}]";
                    break;
                default:
                    formFieldsJSON = "[]";
                    break;
            }


            return CreanceFormDTO.builder()
                    .id(creance.getId())
                    .name(creance.getName())
                    .creancetype(creance.getCreancetype())
                    .creancierName(creancier.getName())
                    .creancierLogo(creancier.getLogo())
                    .formFieldsJSON(formFieldsJSON)// Set the formFieldsJSON string in the Creance object
                    .build();
        }
        return null;
    }
}
