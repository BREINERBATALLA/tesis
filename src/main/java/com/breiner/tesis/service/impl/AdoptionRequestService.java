package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.AdoptionRequestDto;
import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.response.AdoptionRequestDetailsDto;
import com.breiner.tesis.dto.response.AdoptionRequestResponseDto;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.entity.AdoptionRequest;
import com.breiner.tesis.entity.User;
import com.breiner.tesis.enumeration.Status;
import com.breiner.tesis.exception.AdoptionPetAlreadyAdopted;
import com.breiner.tesis.exception.ResourceNotFound;
import com.breiner.tesis.mapper.AdoptionPetMapper;
import com.breiner.tesis.mapper.AdoptionRequestMapper;
import com.breiner.tesis.mapper.UserMapper;
import com.breiner.tesis.repository.AdoptionRequestRepository;
import com.breiner.tesis.service.IAdoptionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AdoptionRequestService implements IAdoptionRequestService {

    private final UserService userService;
    private final AdoptionRequestRepository adoptionRequestRepository;
    private final AdoptionRequestMapper adoptionRequestMapper;
    private final AdoptionPetService adoptionPetService;
    private final AdoptionPetMapper adoptionPetMapper;
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @Override
    public Long sentAdoptionRequest(AdoptionRequestDto adoptionRequestDto, Long adoptionPetId) {
        UserDto currentUser= userService.getCurrentLoggedUser();
        User loggedUser = userMapper.toUserFromUserDto(currentUser);
        AdoptionPet adoptionPet = adoptionPetMapper.toAdoptionPetFromAdoptionResponseDto(
                adoptionPetService.getAdoptionPetById(adoptionPetId)
        );

        if(adoptionPet.isAdopted()) {
            throw new AdoptionPetAlreadyAdopted("Adoption Pet is already adopted");
        }

        AdoptionRequest adoptionRequest = AdoptionRequest.builder()
                .adoptionForm(adoptionRequestMapper.toAdoptionForm(adoptionRequestDto.adoptionForm()))
                .familyReferenceForm(adoptionRequestMapper.toFamilyReferenceForm(adoptionRequestDto.familyReferenceForm()))
                .user(loggedUser)
                .adoptionPet(adoptionPet)
                .status(Status.SENT)
                .build();

        String message = "NUEVA SOLICITUD DE ADOPCIÓN\n" +
                "ACABA DE LLEGAR UNA NUEVA SOLICITUD DE ADOPCIÓN, VE A VERLA!!\n\n" +
                "Datos de la solicitud:\n" +
                "Nombre del usuario: " + adoptionRequest.getUser().getFirstName() + "\n" +
                "Nombre de la mascota: " + adoptionRequest.getAdoptionPet().getName() + "\n" +
                "Condición adopción mascota: " + adoptionRequest.getAdoptionPet().getAdoptionCondition();


        notificationService.publishMessageNewAdoptionRequest(message,
                "ACABA DE LLEGAR UNA NUEVA SOLICITUD DE ADOPCIÓN");

        ///enviar notificacion a todos los usuarios con rol admin!! Topico cuando se envia una
        //adopción request, para admin y para el usuario que la envio, así
        //podre notificarlo cuando se setee el estado..
        return saveAdoptionRequest(adoptionRequest).getIdAdoptionRequest();
    }

    @Override
    public AdoptionRequest getAdoptionRequestById(Long id) {
        return adoptionRequestRepository.findById(id).orElse(null);
    }

    @Override
    public AdoptionRequest saveAdoptionRequest(AdoptionRequest adoptionRequest) {
        return adoptionRequestRepository.save(adoptionRequest);
    }

    @Override
    public AdoptionRequestDetailsDto getAdoptionRequestDetails(Long idAdoptionRequest) {
       AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(idAdoptionRequest).orElseThrow(
               () -> new ResourceNotFound("AdoptionRequest with id "+idAdoptionRequest+" wasn't found")
       );

        return adoptionRequestMapper.
                toAdoptionRequestDetailsDto(adoptionRequest.getIdAdoptionRequest(),adoptionRequest.getStatus(), adoptionRequest.getAdoptionPet());
    }

    @Override
    public List<AdoptionRequestResponseDto> getAllAdoptionRequestByUser() {
        String email = userService.getCurrentLoggedUser().email();
        User user =userService.findByEmail(email).orElseThrow(
                () -> new ResourceNotFound("User with email "+ email+ " wasn't found")
        );
        return adoptionRequestRepository.findAllByUser_IdUser(user.getIdUser()).stream().map(
                adoptionRequestMapper::toAdoptionRequestResponseDto
        ).toList();
    }

    @Override
    public List<AdoptionRequestDetailsDto> getAll() {
        List<AdoptionRequest> adoptionRequests = adoptionRequestRepository.findAll();
        return adoptionRequests.stream()
                .map(adoptionRequest -> adoptionRequestMapper.
                        toAdoptionRequestDetailsDto(adoptionRequest.getIdAdoptionRequest(), adoptionRequest.getStatus(), adoptionRequest.getAdoptionPet()))
                .toList();
    }

}
