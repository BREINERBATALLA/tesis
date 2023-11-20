package com.breiner.tesis.dto.request;

import com.breiner.tesis.entity.Address;
import com.breiner.tesis.enumeration.Role;
import jakarta.persistence.*;

public record UserDto(

                 Long idUser,
                 String identificationNumber,

                 String email,

                 String firstName,

                 String lastName,

                 AddressDto address

) {
}
