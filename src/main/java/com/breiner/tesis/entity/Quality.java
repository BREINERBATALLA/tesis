package com.breiner.tesis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idQuality;

    private String description;

    @ManyToOne
    @JsonIgnore
    @JsonManagedReference
    @JoinColumn(name = "idPetAdoption") //org.hibernate.dialect.MySQLDialect
    private AdoptionPet petAdoption;
}
