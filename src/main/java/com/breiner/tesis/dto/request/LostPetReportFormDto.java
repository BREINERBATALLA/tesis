package com.breiner.tesis.dto.request;

public record LostPetReportFormDto(
        AddressDto addressLastPlaceSeen,
        String additionalInformation,
        boolean atYourDisposition,

        Long idLostPet

     //   Long idUser

) { }
//Lost pet -- send report.