package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.LostPetDto;
import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.request.UpdateStatusRequestDto;
import com.breiner.tesis.dto.response.LostPetResponseDto;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.entity.AdoptionRequest;
import com.breiner.tesis.entity.LostPet;
import com.breiner.tesis.enumeration.Status;
import com.breiner.tesis.mapper.AdoptionPetMapper;
import com.breiner.tesis.mapper.LostPetMapper;
import com.breiner.tesis.repository.LostPetReportFormRepository;
import com.breiner.tesis.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService implements IAdminService {

    private final NotificationService notificationService;
    private final IUserService userService;
    private final IAdoptionRequestService adoptionRequestService;
    private final AdoptionPetMapper adoptionPetMapper;
    private final LostPetReportFormRepository lostPetReportFormRepository;
    private final LostPetMapper lostPetMapper;
    private final ILostPetService lostPetService;
    private final IFileService fileService;
    private final lFundationInfoService fundationInfoService;

    @Override
    public void updateStatusAdoptionRequest(Long idAdoptionRequest, UpdateStatusRequestDto requestDto) {
        //Long adoptantUserId = userService.getCurrentLoggedUser().idUser();
        //String userEmail = userService.getCurrentLoggedUser().email();

        AdoptionRequest adoptionRequest =adoptionRequestService.getAdoptionRequestById(idAdoptionRequest);
        String userEmail = adoptionRequest.getUser().getEmail();
        AdoptionPet adoptionPet = adoptionRequest.getAdoptionPet();

        adoptionRequest.setStatus(requestDto.status());
        if(requestDto.status().equals(Status.ACCEPTED)) {
            adoptionPet.setAdopted(true);
        }
        if(requestDto.status().equals(Status.REJECTED)) {
            adoptionPet.setAdopted(false);
        }

        adoptionRequestService.saveAdoptionRequest(adoptionRequest);
        notificationService.sendEmail(requestDto.message() +"Dirección: " +
                        fileService.setProperties().getProperty("address")+" \n",
                "ACTUALIZACIÓN DEL ESTADO DE SU SOLICITUD DE ADOPCIÓN",
                userEmail);
    }


    @Override
    public void updateFoundationInformation(String key, String content) {
        fundationInfoService.updateFundationInfo(key, content);
    }

    @Override
    public List<LostPetReportFormDto> getAllReportLostPetNotFound() {
        return lostPetReportFormRepository.findAll().stream().
                filter(lostPetReportForm -> !lostPetReportForm.getLostPet().isFound())
                .map(lostPetMapper::toLostPetReportFormDto)
                .toList();
    }

    @Override
    public void updateStatusLostPetAndSendNotification(Long idLostPet) {

        //envio email a dueño de mascota, mascota encontrada.
        notificationService.sendEmail("SU MASCOTA HA SIDO ENCONTRADA,\n" +
                "FAVOR ACERCARSE A NUESTRAS INSTALACIONES:\n" +
                "Dirección: " + fileService.setProperties().getProperty("address")+" \n"+
                "Teléfono: " + fileService.setProperties().getProperty("telephone")+" \n",
                "SU MASCOTA HA SIDO ENCONTRADA",
                lostPetService.getOwnerEmailByIdLostPet(idLostPet));
        lostPetService.updateStatusLostPet(idLostPet);

    }


}
