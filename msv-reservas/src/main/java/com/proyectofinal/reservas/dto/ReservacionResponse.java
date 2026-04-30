package com.proyectofinal.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyectofinal.common.dto.DatosHabitacion;
import com.proyectofinal.common.dto.DatosHuesped;

import java.util.Date;

public record ReservacionResponse(
        Long id,
        DatosHuesped huesped,
        DatosHabitacion habitacion,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaIngreso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaSalida,
        String estadoReservacion
) {
}
