package com.ensa.jibi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Table(name = "backOffice")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("backOffice")
public class BackOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backOffice_id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;
    private String username;
    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.EAGER)
    @JoinColumn(name = "backOffice_id")
    private List<Agent> agents=new ArrayList<>();


    public BackOffice(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
