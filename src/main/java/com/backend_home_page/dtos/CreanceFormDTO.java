package com.backend_home_page.dtos;


import com.backend_home_page.entities.CreanceType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;


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