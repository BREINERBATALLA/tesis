package com.breiner.tesis.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record LostPetDto(
        String description,
        MultipartFile photoFile,
        String street,

        String city,

        String department,

        String neighborhood,
       String name,
       String race,
       boolean found,

       String ownerEmail
        ) { }