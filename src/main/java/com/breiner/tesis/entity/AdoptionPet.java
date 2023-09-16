package com.breiner.tesis.entity;

import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class AdoptionPet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdoptionPet;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String photo;

    private boolean adopted; //where clause to show available pets

    private Integer ageInMonths; //guardar fecha de nacimiento y a partir de allí, calcular edad en meses.

    private LocalDate birthDate;

    private String coatType; //tipo de pelaje

    private String eyeColor;

    private String adoptionCondition;

    private String race;

    @Enumerated(EnumType.STRING)
    private TypePet typePet;

    private String description;

    private String name;

    @OneToMany(mappedBy = "petAdoption", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Quality> qualityList;


}
