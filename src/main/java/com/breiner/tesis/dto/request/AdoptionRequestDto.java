package com.breiner.tesis.dto.request;

import com.breiner.tesis.enumeration.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record AdoptionRequestDto(
        AdoptionFormDto adoptionForm,
        FamilyReferenceFormDto familyReferenceForm,
        Long adoptionPetId //id porque ya existen.
        //Long idUser lo obtengo de el usuario logueado.
        //Long adoptionPetId lo envio por path
) {
}
