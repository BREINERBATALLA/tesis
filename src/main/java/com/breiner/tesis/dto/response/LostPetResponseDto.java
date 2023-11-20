package com.breiner.tesis.dto.response;

import com.breiner.tesis.dto.request.AddressDto;

public record LostPetResponseDto(
        Long idLostPet,
        String photo,
        String description,
        AddressDto addressLastPlaceSeen,
        String name,
        String race,
        boolean found
) {
}
