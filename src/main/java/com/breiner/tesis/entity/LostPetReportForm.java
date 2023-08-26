package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LostPetReportForm {

    @Id
    private Long idLostPetReportForm;

    private String addressLastPlaceSeen;

    private String additionalInformation;

    private boolean atYourDisposition;

    @OneToOne
    @JoinColumn(name = "idLostPet")
    private LostPet lostPet;
}
