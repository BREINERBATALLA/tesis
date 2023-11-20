package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.AdoptionRequestDto;
import com.breiner.tesis.dto.response.AdoptionRequestDetailsDto;
import com.breiner.tesis.dto.response.AdoptionRequestResponseDto;
import com.breiner.tesis.entity.AdoptionRequest;
import com.breiner.tesis.service.IAdoptionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adoption-requests")
public class AdoptionRequestController {

    private final IAdoptionRequestService adoptionRequestService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(path = "/{id-adoption-pet}")
    public ResponseEntity<Long> register(
            @RequestBody AdoptionRequestDto adoptionRequestDto,
            @PathVariable(value = "id-adoption-pet") Long idPet
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adoptionRequestService.sentAdoptionRequest(adoptionRequestDto, idPet));
    }


    @GetMapping("/user")
    public ResponseEntity<List<AdoptionRequestResponseDto>> getAdoptionRequestsUser(){
        return ResponseEntity.ok(adoptionRequestService.getAllAdoptionRequestByUser());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<AdoptionRequestDetailsDto>> getAdoptionRequests(){
        return ResponseEntity.ok(adoptionRequestService.getAll());
    }

}
