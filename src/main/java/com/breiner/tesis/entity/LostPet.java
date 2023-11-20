package com.breiner.tesis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LostPet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLostPet;

    private String description;

    private String photo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address addressLastPlaceSeen;

    private String name;

    private String race;

    private boolean found;

    private String ownerEmail;
    @PrePersist
    public void setDefaultValues() {
        this.found = false;
    }

}
