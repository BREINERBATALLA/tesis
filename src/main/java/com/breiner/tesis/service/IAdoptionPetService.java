package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.AdoptionPetDto;
import com.breiner.tesis.dto.response.AdoptionPetPresentationDto;
import com.breiner.tesis.dto.response.AdoptionPetResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAdoptionPetService {
    AdoptionPetResponseDto getAdoptionPetById(Long idAdoptionPet);

    AdoptionPetResponseDto saveAdoptionPet (AdoptionPetDto adoptionPetDto);

    List<AdoptionPetPresentationDto> getAllAdoptionPets();

    void deleteById(Long id);


    public List<AdoptionPetPresentationDto> getAllAdoptionPetsBySize(Integer size);

    public List<AdoptionPetPresentationDto> getAllAdoptionPetsDewordmedAndVacinnated();

    List<AdoptionPetPresentationDto> getAllAdoptionPetsAdopted();

    public List<AdoptionPetPresentationDto> getAllAdoptionPetsByPet(String pet);

    public List<AdoptionPetPresentationDto> getAllAdoptionPetsBySex(String sex);

    public List<AdoptionPetPresentationDto> getAllAdoptionPetsByRace(String race);

    public String uploadPhoto(MultipartFile file, Long idAdoptionPet);
}
