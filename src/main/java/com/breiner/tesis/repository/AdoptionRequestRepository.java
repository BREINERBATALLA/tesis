package com.breiner.tesis.repository;

import com.breiner.tesis.dto.response.ReportDto;
import com.breiner.tesis.entity.AdoptionRequest;
import com.breiner.tesis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {

    List<AdoptionRequest> findAllByUser_IdUser(Long Iduser);
    @Query("SELECT NEW com.breiner.tesis.dto.response.ReportDto(ar.idAdoptionRequest, ar.dateAdoptionRequest, ar.status, ar.user.identificationNumber, ar.adoptionPet.name, ar.adoptionPet.idAdoptionPet) " +
            "FROM AdoptionRequest ar")
    List<ReportDto> listAdoptionRequestReport();
}
