package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Quality {

    @Id
    private Long idQuality;

    private String description;

    @ManyToOne
    @JoinColumn(name = "idPetAdoption")
    private PetAdoption petAdoption;
}
