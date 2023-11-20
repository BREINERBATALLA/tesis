package com.breiner.tesis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LostPetReportForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idLostPetReportForm;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address addressLastPlaceSeen;

    private String additionalInformation;

    private boolean atYourDisposition;

    @ManyToOne
    @JoinColumn(name = "idLostPet")
    private LostPet lostPet;

    @ManyToOne()
    @JoinColumn(name = "idUser")
    private User user;
}
