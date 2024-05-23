package com.ensa.jibi.dto;


import com.ensa.jibi.model.CreanceType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreanceFormDTO {

    private Long id;
    private String name;
    private CreanceType creancetype;
    private String formFieldsJSON; // JSON string representing the form fields
    private String creancierName;
    private String creancierLogo;

}