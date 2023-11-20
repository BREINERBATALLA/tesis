package com.breiner.tesis.dto.request;

import com.breiner.tesis.entity.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public record FamilyReferenceFormDto(
                 String cellphoneNumber,
                 String firstName,
                 String lastName,
                 String email,
                 String cellphone,
                 String identificationNumber,
                 AddressDto address,
                 String typeKinship
) {
}


