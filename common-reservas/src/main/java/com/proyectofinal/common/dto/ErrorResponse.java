package com.proyectofinal.common.dto;

public record ErrorResponse(
        int codigo,
        String mensaje
) {
}
