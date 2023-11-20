package com.breiner.tesis.mapper;

import com.breiner.tesis.dto.request.*;
import com.breiner.tesis.dto.response.AdoptionRequestDetailsDto;
import com.breiner.tesis.dto.response.AdoptionRequestResponseDto;
import com.breiner.tesis.entity.AdoptionForm;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.entity.AdoptionRequest;
import com.breiner.tesis.entity.FamilyReferenceForm;
import com.breiner.tesis.enumeration.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdoptionRequestMapper {

    @Mapping(target = "idFamilyReferenceForm", ignore = true )
    FamilyReferenceForm toFamilyReferenceForm(FamilyReferenceFormDto dto);

    @Mapping(target = "idAdoptionForm", ignore = true )
    AdoptionForm toAdoptionForm(AdoptionFormDto dto);

    AdoptionRequestResponseDto toAdoptionRequestResponseDto(AdoptionRequest adoptionRequest);

    @Mapping(target = "photo", source = "adoptionPet.photo")
    @Mapping(source = "adoptionPet.adopted" , target = "isAdopted" )
    @Mapping(source = "adoptionPet.name" , target = "name" )
    @Mapping(source = "idAdoptionRequest" , target = "idAdoptionRequest" )
    @Mapping(source = "status" , target = "status" )
    AdoptionRequestDetailsDto toAdoptionRequestDetailsDto(Long idAdoptionRequest, Status status, AdoptionPet adoptionPet);


}
