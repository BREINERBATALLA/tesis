package com.breiner.tesis.entity;

import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class AdoptionPet {

    @Id
    private Long idAdoptionPet;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String photo;

    private boolean adopted; //where clause to show available pets

   // private Integer edadEnMeses; //guardar fecha de nacimiento y a partir de allí, calcular edad en meses.
    private LocalDate birthDate;

    private String typeFur;

    private String eyeColor;

    private String adoptionCondition;

    private String raca;

    @Enumerated(EnumType.STRING)
    private TypePet typePet;

    private String description;

    private String name;

    @OneToMany(mappedBy = "mascotaAdopcion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Quality> qualityList;


}
