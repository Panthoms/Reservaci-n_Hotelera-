package com.proyectofinal.reservas.enums;

import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReservacion {
    CONFIRMADA(1L, "Reserva creada"),
    EN_CURSO(2L, "Check-in realizado"),
    FINALIZADA(3L, "Check-in realizado"),
    CANCELADA(4L, "Reserva cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoReservacion obtenerDisponibilidadPorCodigo(Long codigo) {
        for (EstadoReservacion d : values()) {
            if (d.codigo == codigo) {
                return d;
            }
        }
        throw new RecursoNoEncontradoException("Código de disponibilidad no válido: " + codigo);
    }

    public static EstadoReservacion obtenerDisponibilidadPorDescripcion(Long codigo) {
        for (EstadoReservacion estado : EstadoReservacion.values()) {
            if (estado.getCodigo() == codigo) {
                return estado;
            }
        }
        throw new RecursoNoEncontradoException("Código de disponibilidad no válido: " + codigo);
    }
}
