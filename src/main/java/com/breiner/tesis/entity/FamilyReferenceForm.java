package com.breiner.tesis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FamilyReferenceForm {

    @Id
    private Long idFamilyReferenceForm;

    private String cellphoneNumber;

    private String firstName;

    private String lastName;

    private String typeKinship;
}
