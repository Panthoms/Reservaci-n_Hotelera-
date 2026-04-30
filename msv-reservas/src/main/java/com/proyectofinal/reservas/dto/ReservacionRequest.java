package com.proyectofinal.reservas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record ReservacionRequest(
        @NotNull(message = "El id del paciente es requerido")
        @Positive(message = "El id del paciente debe ser positivo")
        Long idHuesped,

        @NotNull(message = "El id del médico es requerido")
        @Positive(message = "El id del médico debe ser positivo")
        Long idHabitaciones,

        @NotNull(message = "La fecha de ingreso es requerida")
        @FutureOrPresent(message = "La fecha de la cita debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaIngreso,

        @NotNull(message = "La fecha de salida es requerida")
        @FutureOrPresent(message = "La fecha de la cita debe ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        Date fechaSalida,

        @Positive(message = "El id del estado de la reservacion debe ser positivo")
        Long estadoReservacion
) {

}
