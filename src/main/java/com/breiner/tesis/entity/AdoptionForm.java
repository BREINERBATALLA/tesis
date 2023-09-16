package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AdoptionForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdoptionForm;

    private String activitiesPlanToDoWithThePet;

    private boolean includesPetInCaseOfMove;

    private String reasonOfAdoption;

    private Integer weeklyHoursAvailableForThePet;
}
