package com.backend_home_page.services;

import com.backend_home_page.dtos.CreanceFormDTO;
import com.backend_home_page.entities.Creance;
import com.backend_home_page.entities.Creancier;
import com.backend_home_page.repo.CreanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                            "{\"key\":\"amoungit reset HEAD .\ngit reset HEAD .\nt\",\"label\":\"Montant du don\",\"type\":\"number\",\"placeholder\":\"Enter donation amount\"}]";
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
