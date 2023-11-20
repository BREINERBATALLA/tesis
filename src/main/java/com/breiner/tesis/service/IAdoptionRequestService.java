package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.AdoptionRequestDto;
import com.breiner.tesis.dto.response.AdoptionRequestDetailsDto;
import com.breiner.tesis.dto.response.AdoptionRequestResponseDto;
import com.breiner.tesis.entity.AdoptionRequest;

import java.util.List;

public interface IAdoptionRequestService {

    Long sentAdoptionRequest(AdoptionRequestDto adoptionRequestDto, Long adoptionPetId);

    AdoptionRequest getAdoptionRequestById(Long id);

    AdoptionRequest saveAdoptionRequest(AdoptionRequest adoptionRequest);

    AdoptionRequestDetailsDto getAdoptionRequestDetails(Long idAdoptionRequest);

    List<AdoptionRequestResponseDto> getAllAdoptionRequestByUser();

    List<AdoptionRequestDetailsDto> getAll();
}
