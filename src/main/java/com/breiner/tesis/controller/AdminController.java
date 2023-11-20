package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.LostPetReportFormDto;
import com.breiner.tesis.dto.request.UpdateStatusRequestDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.response.AdoptionRequestDetailsDto;
import com.breiner.tesis.dto.response.AdoptionRequestResponseDto;
import com.breiner.tesis.dto.response.ResponseUserDto;
import com.breiner.tesis.service.IAdminService;
import com.breiner.tesis.service.IAdoptionRequestService;
import com.breiner.tesis.service.IFileService;
import com.breiner.tesis.service.INotificationService;
import com.breiner.tesis.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final IAdminService adminService;
    private final INotificationService notificationService;
    private final AuthenticationService authenticationService;
    private final IAdoptionRequestService adoptionRequestService;
    private final IFileService fileService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseUserDto> register(
            @RequestBody UserRegisterDto userRegisterDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerAdminUser(userRegisterDTO));
    }

    @GetMapping(path = "/requests/{id}")
    public ResponseEntity<AdoptionRequestDetailsDto> getAdoptionDetails(@PathVariable Long id){
        return ResponseEntity.ok(adoptionRequestService.getAdoptionRequestDetails(id));
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<String> updateStatus(@RequestBody UpdateStatusRequestDto requestDto,
                                               @PathVariable Long id){
        adminService.updateStatusAdoptionRequest(id, requestDto);
        return ResponseEntity.ok("STATUS UPDATED");
    }

    @PostMapping("/boostrap-csv")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file){
        fileService.uploadDataFromCSV(file);
        return ResponseEntity.ok(" DATA HAS BEEN SAVED SUCCESFULLY");
    }

    @GetMapping(path = "/report")
    public ResponseEntity<String> getAdoptionDetails(@RequestParam("format") String  format){
        return ResponseEntity.ok(fileService.exportReport(format));
    }

    @GetMapping(path = "/requests")
    public ResponseEntity<List<AdoptionRequestDetailsDto>> getAdoptionDetails(){
        return ResponseEntity.ok(adoptionRequestService.getAll());
    }

    @GetMapping(path = "/lostpets/reports")
    public ResponseEntity<List<LostPetReportFormDto>> getLostPetReports(){
        return ResponseEntity.ok(adminService.getAllReportLostPetNotFound());
    }

    @PutMapping("/lostpets/{id}")
    public ResponseEntity<String> updateStatusLostPet(@PathVariable Long id){
        adminService.updateStatusLostPetAndSendNotification(id);
        return ResponseEntity.ok("STATUS UPDATED");
    }

    @PutMapping("/fundation")
    public ResponseEntity<String> updateFundationInfo(@Valid  @RequestParam("key") String key,
                                                      @Valid @RequestParam("content") String content){
        adminService.updateFoundationInformation(key,content);
        return ResponseEntity.ok("STATUS INFORMATION");
    }


}
