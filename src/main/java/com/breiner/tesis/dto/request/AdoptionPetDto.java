package com.breiner.tesis.dto.request;

import com.breiner.tesis.entity.Quality;
import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@Builder
public record AdoptionPetDto(
        @Enumerated(EnumType.STRING)
        Sex sex,
         MultipartFile photoFile,

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

        String size,
        List<QualityDto> qualityList
) {
}
