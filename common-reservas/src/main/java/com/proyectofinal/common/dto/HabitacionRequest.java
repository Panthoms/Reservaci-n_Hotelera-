package com.proyectofinal.common.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record HabitacionRequest(

        @NotNull(message = "El numero de abitacion es requerido")
        @Positive(message = "El numero de habitacion tiene que ser positivo")
        Integer numeroHabitacion,

        @NotBlank(message = "El tipó de habitacion es requerido")
        @Size(message = "El tamaño de caracteres no debe ser mayor a 20", max = 20, min = 3)
        String tipoHabitacion,

        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio no puede ser negativo")
        @Min(value = 1)
        BigDecimal precio,

        @NotNull(message = "La capcidad es requerida")
        @Positive(message = "La capacidad no puede ser negativo")
        @Min(value = 1)
        Integer capacidad
) {
}
