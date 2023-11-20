package com.breiner.tesis.dto.response;

import com.breiner.tesis.enumeration.Sex;

public record AdoptionPetPresentationDto(Long idAdoptionPet, Integer ageInMonths, String name,
                                         String race, Sex sex, String photo) {
}
