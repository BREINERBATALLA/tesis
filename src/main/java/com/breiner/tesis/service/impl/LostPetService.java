package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.LostPetDto;
import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.response.LostPetResponseDto;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.entity.LostPet;
import com.breiner.tesis.entity.LostPetReportForm;
import com.breiner.tesis.entity.User;
import com.breiner.tesis.exception.ResourceNotFound;
import com.breiner.tesis.mapper.LostPetMapper;
import com.breiner.tesis.mapper.UserMapper;
import com.breiner.tesis.repository.LostPetReportFormRepository;
import com.breiner.tesis.repository.LostPetRespository;
import com.breiner.tesis.service.IFileService;
import com.breiner.tesis.service.ILostPetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LostPetService implements ILostPetService {

    private final LostPetRespository lostPetRespository;
    private final LostPetReportFormRepository lostPetReportFormRepository;
    private final UserService userService;
    private final LostPetMapper lostPetMapper;
    private final IFileService fileService;
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @Override
    public LostPetResponseDto getLostPetById(Long idLostPet) {
        return lostPetMapper.toLostPetResponseDto(lostPetRespository.findById(idLostPet).
                orElseThrow(()-> new ResourceNotFound("Lost pet wasn't found")));
    }

    @Override
    public LostPetResponseDto saveLostPet(LostPetDto lostPetDto) {
        String fileName = fileService.uploadFile(lostPetDto.photoFile());
        LostPet lostPet = lostPetMapper.toLostPet(lostPetDto);
        lostPet.setPhoto(fileName);
        return lostPetMapper.toLostPetResponseDto(lostPetRespository.save(lostPetMapper.toLostPet(lostPetDto)));
    }

    @Override
    public List<LostPetResponseDto> getAllLostPets() {
        return lostPetRespository.findAll().stream().
                filter(lostpet -> !lostpet.isFound())
                .map(lostPetMapper::toLostPetResponseDto
        ).toList();
    }

    @Override
    public void deleteById(Long idLostPet) {
        lostPetRespository.deleteById(idLostPet);
    }

    @Override
    public Long sentReportLosPet(LostPetReportFormDto lostPetReportFormDto, Long idLostPet) {
        UserDto currentUser= userService.getCurrentLoggedUser();
        User loggedUser = userMapper.toUserFromUserDto(currentUser);
        LostPet lostPet = lostPetRespository.findById(idLostPet).
                orElseThrow(() -> new ResourceNotFound("Not found"));

        LostPetReportForm lostPetReportForm = lostPetMapper.toLostPetReportFrom(lostPetReportFormDto);
        lostPetReportForm.setLostPet(lostPet);
        lostPetReportForm.setUser(loggedUser);
        //enviarla al admin.
        Long id = lostPetReportFormRepository.save(lostPetReportForm).getIdLostPetReportForm();
        notificationService.publishMessageNewAdoptionRequest("REPORTE DE MASCOTA PERDIDA",
                "ACABA DE LLEGAR UN REPORTE CON INFORMACIÓN DE UNA MASCOTA PERDIDA!" +
                        "CON ID: "+ id);
        return id;
    }


    public String getOwnerEmailByIdLostPet(Long idLostPet) {
        return lostPetRespository.findById(idLostPet).
                orElseThrow(()-> new ResourceNotFound("Lost pet wasn't found"))
                .getOwnerEmail();
    }

    @Override
    public void updateStatusLostPet(Long idLostPet) {
        LostPet lostPet = lostPetRespository.findById(idLostPet).
                orElseThrow(()-> new ResourceNotFound("Lost pet wasn't found"));
        lostPet.setFound(true);
        lostPetRespository.save(lostPet);
    }


}
