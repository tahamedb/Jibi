package com.ensa.jibi.dto;

import com.ensa.jibi.model.Creancier;
import com.ensa.jibi.model.CreancierType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreancierDTO {

    private Long id;
    private String name;
    private String logo;
    private CreancierType categorieCreancier;

    private List<CreanceDTO> creances;

    public CreancierDTO(Creancier creancier) {
        this.id = creancier.getId();
        this.name = creancier.getName();
        this.logo = creancier.getLogo();
        this.categorieCreancier=creancier.getCategorieCreancier();
        this.creances = creancier.getCreances().stream()
                .map(CreanceDTO::new)
                .collect(Collectors.toList());
    }
}
