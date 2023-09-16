package com.breiner.tesis.entity;

import com.breiner.tesis.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAdoptionRequest;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SENT ;

    @CreationTimestamp
    private LocalDate dateAdoptionRequest;

    @ManyToOne()
    @JoinColumn(name = "idAdoptionPet")
    private AdoptionPet adoptionPet;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "idAdoptionForm")
    private AdoptionForm adoptionForm;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "idFamilyReferenceForm")
    private FamilyReferenceForm familyReferenceForm;

    @ManyToOne()
    @JoinColumn(name = "idUser")
    private User user;

}
