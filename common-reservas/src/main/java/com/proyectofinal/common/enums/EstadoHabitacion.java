package com.proyectofinal.common.enums;

import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {
    DISPONIBLE(1L, "HABITACION DISPONIBLE PARA USAR"),
    OCUPADA(2L, "HABITACION EN ESTADO DE USO"),
    LIMPIEZA(3L, "HABITACION EN ESTADO DELIMPIEZA"),
    MANTENIMIENTO(4L, "HABITACIOEN EN MANTENIMIENTO");

    private final Long codigo;

    private final String descripcion;

    public static EstadoHabitacion obtenerDisponibilidadPorCodigo(Long codigo) {
        for (EstadoHabitacion d : values()) {
            if (d.codigo == codigo) {
                return d;
            }
        }
        throw new RecursoNoEncontradoException("Código de disponibilidad no válido: " + codigo);
    }

    public static EstadoHabitacion obtenerDisponibilidadPorDescripcion(Long codigo) {
        for (EstadoHabitacion estado : EstadoHabitacion.values()) {
            if (estado.getCodigo() == codigo) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException("Código de disponibilidad no válido: " + codigo);
    }
}
