package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.LostPetDto;
import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.response.LostPetResponseDto;
import com.breiner.tesis.service.ILostPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lostpets")
public class LostPetController {

    private final ILostPetService lostPetService;

    @GetMapping("/{idLostPet}")
    public ResponseEntity<LostPetResponseDto> getLostPetById(@PathVariable Long idLostPet) {
        LostPetResponseDto lostPet = lostPetService.getLostPetById(idLostPet);
        return ResponseEntity.ok(lostPet);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LostPetResponseDto> saveLostPet(@ModelAttribute LostPetDto lostPetDto) {
        LostPetResponseDto savedLostPet = lostPetService.saveLostPet(lostPetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLostPet);
    }

    @GetMapping
    public ResponseEntity<List<LostPetResponseDto>> getAllLostPets() {
        List<LostPetResponseDto> lostPets = lostPetService.getAllLostPets();
        return ResponseEntity.ok(lostPets);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idLostPet}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idLostPet) {
        lostPetService.deleteById(idLostPet);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idLostPet}/report")
    public ResponseEntity<Long> sentReportLostPet(@RequestBody LostPetReportFormDto lostPetReportFormDto,
                                                  @PathVariable("idLostPet") Long idLostPet) {
        Long newReportId = lostPetService.sentReportLosPet(lostPetReportFormDto, idLostPet);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReportId);
    }

}
