package com.breiner.tesis.mapper;

import com.breiner.tesis.dto.request.LostPetDto;
import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.response.LostPetResponseDto;
import com.breiner.tesis.entity.LostPet;
import com.breiner.tesis.entity.LostPetReportForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LostPetMapper {

    @Mapping(target = "user", ignore = true )
    @Mapping(target = "lostPet", ignore = true )
    @Mapping(target = "idLostPetReportForm", ignore = true )
    LostPetReportForm toLostPetReportFrom (LostPetReportFormDto  formDto);

    @Mapping(target = "photo", ignore = true )
    @Mapping(target = "addressLastPlaceSeen.street", source = "street")
    @Mapping(target = "addressLastPlaceSeen.city", source = "city")
    @Mapping(target = "addressLastPlaceSeen.department", source = "department")
    @Mapping(target = "addressLastPlaceSeen.neighborhood", source = "neighborhood")
    LostPet toLostPet(LostPetDto lostPetDto);

    LostPetResponseDto toLostPetResponseDto (LostPet lostPet);

    @Mapping(target = "idLostPet", source = "lostPetReportForm.lostPet.idLostPet")
    LostPetReportFormDto toLostPetReportFormDto(LostPetReportForm lostPetReportForm);
}