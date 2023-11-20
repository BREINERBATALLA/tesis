package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.request.UpdateStatusRequestDto;

import java.util.List;

public interface IAdminService {

    void updateStatusAdoptionRequest(Long idAdoptionRequest, UpdateStatusRequestDto updateStatusRequestDto);

    void updateFoundationInformation(String key, String content);

    List<LostPetReportFormDto> getAllReportLostPetNotFound();

    void updateStatusLostPetAndSendNotification(Long idLostPet);
}
