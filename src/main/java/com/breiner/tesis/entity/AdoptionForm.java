package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdoptionForm {

    @Id
    private Long idAdoptionForm;

    private String activitiesPlanToDoWithThePet;

    private boolean includesPetInCaseOfMove;

    private String reasonOfAdoption;

    private Integer weeklyHoursAvailableForThePet;
}
