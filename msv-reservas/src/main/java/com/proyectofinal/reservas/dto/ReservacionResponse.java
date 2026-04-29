package com.proyectofinal.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record ReservacionResponse(
        Long id,
        Long idHuesped,
        Long idHabitaciones,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaIngreso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaSalida,
        String estadoReservacion
) {
}
