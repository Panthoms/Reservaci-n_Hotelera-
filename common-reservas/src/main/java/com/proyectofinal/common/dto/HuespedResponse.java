package com.proyectofinal.common.dto;

public record HuespedResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String tipoDocumento,
        String numeroDocumento,
        String nacionalidad
) {
}
