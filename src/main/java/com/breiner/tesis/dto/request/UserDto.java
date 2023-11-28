package com.breiner.tesis.dto.request;

import com.breiner.tesis.entity.Address;
import com.breiner.tesis.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(

                 Long idUser,
                 @NotBlank(message = "El número de identificación es obligatorio")
                 String identificationNumber,

                 @Email(message = "El formato de email no es válido")
                 String email,

                 String firstName,

                 String lastName,

                 AddressDto address

) {
}
