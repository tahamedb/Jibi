package com.ensa.jibi.dto;


import com.ensa.jibi.model.Creance;
import com.ensa.jibi.model.CreanceType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreanceDTO {

    private Long id;
    private String name;
    private CreanceType creancetype;
    private String formFieldsJSON; // JSON string representing the form fields

    public CreanceDTO(Creance creance) {
        this.id = creance.getId();
        this.name = creance.getName();
        this.creancetype = creance.getCreancetype();
        this.formFieldsJSON= creance.getFormFieldsJSON();
    }

}

