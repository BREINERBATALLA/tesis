package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.AdoptionPetDto;
import com.breiner.tesis.dto.response.AdoptionPetPresentationDto;
import com.breiner.tesis.dto.response.AdoptionPetResponseDto;
import com.breiner.tesis.service.IAdoptionPetService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adoption-pets")
public class AdoptionPetController {

    private final IAdoptionPetService adoptionPetService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create")
    public ResponseEntity<AdoptionPetResponseDto> register(
            @ModelAttribute @Valid AdoptionPetDto adoptionPetDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adoptionPetService.saveAdoptionPet(adoptionPetDto));
    }

    @GetMapping(path = "details/{id}")
    public ResponseEntity<AdoptionPetResponseDto> getById(
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adoptionPetService.getAdoptionPetById(id));
    }

    @GetMapping()
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAll(){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPets());
    }

    @GetMapping("/filter/by-race")
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAllByRace(@PathParam("race")String race){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPetsByRace(race));
    }

    @GetMapping("/filter/by-pet-type")
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAllByTypePet(@PathParam("pet")String pet){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPetsByPet(pet));
    }

    @GetMapping("/filter/by-sex")
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAllBySex(@PathParam("sex")String sex){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPetsBySex(sex));
    }

    @GetMapping("/filter/all/by-condition")
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAllByCondition(){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPetsDewordmedAndVacinnated());
    }

    @GetMapping("/filter/all/size")
    public ResponseEntity<List<AdoptionPetPresentationDto>> getAllBySize(@Valid @PathParam("size")String size){
        return ResponseEntity.ok(adoptionPetService.getAllAdoptionPetsByTam(size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAdoptionPet(@PathVariable Long id) {
        adoptionPetService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<String> uploadAdoptionPetPhoto(@PathParam("photo") MultipartFile photo,
                                                         @PathVariable Long id){
        return ResponseEntity.ok(adoptionPetService.uploadPhoto(photo,id));
    }
}

