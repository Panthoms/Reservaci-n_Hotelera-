package com.proyectofinal.auth.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) {
}
