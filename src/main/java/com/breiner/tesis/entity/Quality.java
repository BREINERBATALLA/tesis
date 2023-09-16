package com.breiner.tesis.entity;

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
    @JoinColumn(name = "idPetAdoption") //org.hibernate.dialect.MySQLDialect
    private AdoptionPet petAdoption;
}
