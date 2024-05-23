package com.backend_home_page.dtos;


import com.backend_home_page.entities.Creance;
import com.backend_home_page.entities.CreanceType;
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

