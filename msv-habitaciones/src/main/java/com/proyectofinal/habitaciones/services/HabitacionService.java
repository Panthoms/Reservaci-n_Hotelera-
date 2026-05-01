package com.proyectofinal.habitaciones.services;

import com.proyectofinal.common.dto.HabitacionRequest;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.common.services.CrudService;

public interface HabitacionService extends CrudService <HabitacionRequest, HabitacionResponse> {

    HabitacionResponse optenerPorIdSinEstado(Long id);

    void actualizarEstado(Long id, Long idEstadoHabitacion);

    void liberarHabitacion(Long id);
}
