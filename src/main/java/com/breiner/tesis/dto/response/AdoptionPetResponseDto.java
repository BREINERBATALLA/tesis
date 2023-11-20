package com.breiner.tesis.dto.response;

import com.breiner.tesis.dto.request.QualityDto;
import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;

public record AdoptionPetResponseDto(

        Long idAdoptionPet,

        Integer ageInMonths,

        String size,

        @Enumerated(EnumType.STRING)
        Sex sex,
        String photo,
        boolean adopted, //; //where clause to show available pets
        LocalDate birthDate,
        String coatType,
        String eyeColor,
        String adoptionCondition,
        String race,
        @Enumerated(EnumType.STRING)
        TypePet typePet,
        String description,
        String name,
        boolean vaccinated,

        boolean dewormed,

        List<QualityResponseDto> qualityList
) {
}
