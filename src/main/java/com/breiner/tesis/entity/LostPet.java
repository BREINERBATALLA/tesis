package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LostPet {

    @Id
    private Long idLostPet;

    private String description;

    private String photo;

    private String addressLastPlaceSeen;

    private String name;

    private String race;
}
