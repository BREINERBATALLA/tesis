package com.breiner.tesis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthUserDto(
        @Email(message = "El formato del correo electrónico no es válido")String email,
        @NotBlank(message = "La contraseña no puede estar en blanco") String password) {
}