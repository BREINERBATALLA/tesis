package com.breiner.tesis.service.impl;

import com.breiner.tesis.dto.request.AdoptionPetDto;
import com.breiner.tesis.dto.response.AdoptionPetPresentationDto;
import com.breiner.tesis.dto.response.AdoptionPetResponseDto;
import com.breiner.tesis.entity.AdoptionPet;
import com.breiner.tesis.enumeration.Sex;
import com.breiner.tesis.enumeration.TypePet;
import com.breiner.tesis.exception.ResourceNotFound;
import com.breiner.tesis.mapper.AdoptionPetMapper;
import com.breiner.tesis.repository.AdoptionPetRepository;
import com.breiner.tesis.service.IAdoptionPetService;
import com.breiner.tesis.service.IFileService;
import com.breiner.tesis.service.INotificationService;
import com.breiner.tesis.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdoptionPetService implements IAdoptionPetService {

    private final AdoptionPetRepository adoptionPetRepository;
    private final AdoptionPetMapper adoptionPetMapper;
    private final IFileService fileService;
    private final INotificationService notificationService;
    private final IUserService userService;
    @Value("${aws.bucket-name}")
    private String bucketName;

    @Override
    public AdoptionPetResponseDto getAdoptionPetById(Long idAdoptionPet){
        return adoptionPetRepository.findById(idAdoptionPet)
                .map(adoptionPetMapper::toAdoptionPetResponseDto)
                .orElseThrow(()->  new ResourceNotFound("Adoption Pet doesn't found"));
    }

    @Override
    public AdoptionPetResponseDto saveAdoptionPet(AdoptionPetDto adoptionPetDto) {
        String fileName = fileService.uploadFile(adoptionPetDto.photoFile());
        AdoptionPet adoptionPet = adoptionPetMapper.toAdoptionPet(adoptionPetDto);
        adoptionPet.setPhoto(fileName);
        adoptionPet.setAdopted(false);
        adoptionPet.setAgeInMonths(AdoptionPet.calculateAgeInMonths(adoptionPetDto.birthDate()));
        String urlPhoto = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        String message = "SE AGREGÓ UNA NUEVA MASCOTA\n" +
                "Tipo de mascota: " + adoptionPet.getTypePet() + "\n" +
                "Raza: " + adoptionPet.getRace() + "\n" +
                "Foto: " + urlPhoto + "\n" +
                "Edad en meses: " + adoptionPet.getAgeInMonths() + "\n" +
                "Condición de adopción: " + adoptionPet.getAdoptionCondition() + "\n" +
                // Otros detalles de la mascota
                "Fin del mensaje";


        notificationService.publishMessageNewPet(message, "SE AGREGO UNA NUEVA MASCOTA" );
        return adoptionPetMapper.toAdoptionPetResponseDto(adoptionPetRepository.
                save(adoptionPet));
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPets() {
        return adoptionPetRepository.findAllByAdoptedIsFalse().stream().
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        adoptionPetRepository.deleteById(id);
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsBySize(Integer size) {
        return adoptionPetRepository.findAllBySizeIsLessThanEqual(size).stream().
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsDewordmedAndVacinnated() {
        return adoptionPetRepository.findAll().stream().
                filter(adoptionPet -> adoptionPet.isVaccinated() && adoptionPet.isDewormed()).
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsAdopted() {
        return adoptionPetRepository.findAllByAdoptedIsTrue().stream().
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }



    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsByPet(String pet) {
        return adoptionPetRepository.findAllByTypePet(TypePet.valueOf(pet)).stream().
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsBySex(String sex) {
        return adoptionPetRepository.findAllBySex(Sex.valueOf(sex)).stream().
                map(adoptionPetMapper::toAdoptionPetsAdoptionPetPresentationDto)
                .toList();
    }

    @Override
    public List<AdoptionPetPresentationDto> getAllAdoptionPetsByRace(String race) {
        return adoptionPetRepository.findAllByRace(race).stream().
                map(a -> adoptionPetMapper.toAdoptionPetsAdoptionPetPresentationDto(a))
                .toList();
    }

    @Override
    public String uploadPhoto(MultipartFile file, Long idAdoptionPet) {
        String fileName = fileService.uploadFile(file);
        AdoptionPet adoptionPet = adoptionPetRepository.findById(idAdoptionPet).
                orElse(null);
       if (adoptionPet != null) {
           adoptionPet.setPhoto(fileName);
           adoptionPetRepository.save(adoptionPet);
           return "Photo was uploaded succesfully";
       } else{
           return "An error has ocurred during upload";
       }
    }


}
