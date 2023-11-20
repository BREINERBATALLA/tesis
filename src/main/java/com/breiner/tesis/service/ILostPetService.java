package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.LostPetDto;
import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.response.LostPetResponseDto;

import java.util.List;

public interface ILostPetService {

    LostPetResponseDto getLostPetById(Long idLostPet);

    LostPetResponseDto saveLostPet (LostPetDto lostPetDto);

    List<LostPetResponseDto> getAllLostPets();

    void deleteById(Long idLostPet);

    Long sentReportLosPet(LostPetReportFormDto lostPetReportFormDto, Long lostPetDto);

    String getOwnerEmailByIdLostPet(Long idLostPet);

    void updateStatusLostPet(Long idLostPet);
}
