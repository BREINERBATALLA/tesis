package com.breiner.tesis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FamilyReferenceForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFamilyReferenceForm;

    private String cellphoneNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String cellphone;

    private String identificationNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    private String typeKinship;
}
