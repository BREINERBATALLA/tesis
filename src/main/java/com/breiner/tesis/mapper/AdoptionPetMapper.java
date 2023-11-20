package com.breiner.tesis.mapper;

import com.breiner.tesis.dto.request.AdoptionPetDto;
import com.breiner.tesis.dto.response.AdoptionPetPresentationDto;
import com.breiner.tesis.dto.response.AdoptionPetResponseDto;
import com.breiner.tesis.entity.AdoptionPet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdoptionPetMapper {
    @Mapping(target ="idAdoptionPet", ignore = true )
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "ageInMonths", ignore = true)
    AdoptionPet toAdoptionPet(AdoptionPetDto adoptionPetDto);

    AdoptionPetResponseDto toAdoptionPetResponseDto(AdoptionPet adoptionPet);

    AdoptionPet toAdoptionPetFromAdoptionResponseDto(AdoptionPetResponseDto dto);

    AdoptionPetPresentationDto toAdoptionPetsAdoptionPetPresentationDto(AdoptionPet adoptionPet);

}
