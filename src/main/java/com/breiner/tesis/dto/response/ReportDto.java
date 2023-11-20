package com.breiner.tesis.dto.response;

import com.breiner.tesis.enumeration.Status;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReportDto {
    private Long idAdoptionRequest;
    private LocalDate adoptionRequestDate;
    private Status status;
    private String adoptantName;
    private String adoptionPetName;
    private Long idAdoptionPet;

    public ReportDto(Long idAdoptionRequest, LocalDate adoptionRequestDate, Status status, String adoptantName,
                     String adoptionPetName, Long idAdoptionPet){
        this.idAdoptionRequest = idAdoptionRequest;
        this.adoptionRequestDate = adoptionRequestDate;
        this.status = status;
        this.adoptantName = adoptantName;
        this.adoptionPetName = adoptionPetName;
        this.idAdoptionPet = idAdoptionPet;
    }
}
