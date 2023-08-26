package com.breiner.tesis.entity;

import com.breiner.tesis.enumeration.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RequestAdoption {

    @Id
    private Long idSolicitud;

    private Status status;

    private LocalDate dateRequestAdoption;

    @ManyToOne()
    @JoinColumn(name = "idAdoptionPet")
    private AdoptionPet adoptionPet;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "idAdoptionForm")
    private AdoptionForm adoptionForm;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "idFamilyReferenceForm")
    private FamilyReferenceForm familyReferenceForm;
}
