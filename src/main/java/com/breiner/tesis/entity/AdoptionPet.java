package com.breiner.tesis.entity;

import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
//@Where(clause = "adopted =false")
public class AdoptionPet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdoptionPet;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String photo;

    private boolean adopted; //where clause to show available pets

    private boolean vaccinated;

    private boolean dewormed; //desparasiado

    private Integer ageInMonths; //guardar fecha de nacimiento y a partir de allí, calcular edad en meses.

    private LocalDate birthDate;

    private String coatType; //tipo de pelaje

    private String eyeColor;

    private String adoptionCondition; //por tipo de mascota, raza y tamaño y si están vacunadas y desparasitadas.

    private String race;

    @Enumerated(EnumType.STRING)
    private TypePet typePet;

    private String description;

    private String size;  //@Valid para validar 3 posibles BIG, LITTLE, MEDIUM

    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "petAdoption", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Quality> qualityList = new ArrayList<>();

    public List<Quality> setQualityList(List<Quality> qualities) {
        for(Quality quality: qualities) {
            quality.setPetAdoption(this);
            qualityList.add(quality);
        }
        // Establecer la relación en ambas direcciones
        return qualities;
    }

    public static Integer calculateAgeInMonths(LocalDate birthDate) {
        Period period = Period.between(birthDate, LocalDate.now());
        return period.getMonths()+period.getYears()*12; //al listar siempre llamar!!
    }
}
