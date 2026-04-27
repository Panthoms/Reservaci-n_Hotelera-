package com.proyectofinal.common.dto;

import java.math.BigDecimal;

public record HabitacionResponse(
        Integer numeroHabitacion,
        String tipoHabitacion,
        BigDecimal precio,
        Integer capacidad,
        String estadoHabitacion
) {
}
