package com.proyectofinal.habitaciones.mappers;

import com.proyectofinal.common.dto.HabitacionRequest;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.common.enums.EstadoHabitacion;
import com.proyectofinal.common.mappers.CommonMapper;
import com.proyectofinal.habitaciones.entities.Habitaciones;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapoer implements CommonMapper <HabitacionRequest,HabitacionResponse, Habitaciones>{
    @Override
    public Habitaciones requestAEntidad(HabitacionRequest request) {
        if (request == null)  return  null;

        return Habitaciones.builder()
                .numeroHabitacion(request.numeroHabitacion())
                .tipoHabitacion(request.tipoHabitacion())
                .precio(request.precio())
                .capacidad(request.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .estadoRegistro()
                .build();

    }

    @Override
    public HabitacionResponse entidadAResponse(Habitaciones entidad) {
        return null;
    }
}
