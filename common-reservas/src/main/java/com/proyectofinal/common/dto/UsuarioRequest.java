package com.proyectofinal.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioRequest(
        @NotBlank(message = "El username es requerido")
        @Size(min = 5, max = 20, message = "El username debe tener entre 5 y 20 caracteres")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
                message = "La contraseña debe contener letras y números"
        )
        String password,

        @NotNull(message = "Los roles son requeridos")
        @Size(min = 1, message = "Debe tener al menos un rol")
        Set<
                @Pattern(regexp = "ADMIN|USER", message = "El rol debe ser ADMIN o USER")
                        String
                > roles
) {
}
