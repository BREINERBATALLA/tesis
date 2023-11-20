package com.breiner.tesis.dto.request;

public record AddressDto(
        Long idAddress,
         String street,

         String city,

         String department,

         String neighborhood
) {
}
