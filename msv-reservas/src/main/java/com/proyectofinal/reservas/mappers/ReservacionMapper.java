package com.proyectofinal.reservas.mappers;

import com.proyectofinal.common.dto.DatosHabitacion;
import com.proyectofinal.common.dto.DatosHuesped;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.common.mappers.CommonMapper;
import com.proyectofinal.reservas.dto.ReservacionRequest;
import com.proyectofinal.reservas.dto.ReservacionResponse;
import com.proyectofinal.reservas.entities.Reservas;
import com.proyectofinal.reservas.enums.EstadoReservacion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservacionMapper implements CommonMapper<ReservacionRequest, ReservacionResponse, Reservas> {

    @Override
    public Reservas requestAEntidad(ReservacionRequest request) {
        if(request == null)return null;

        return Reservas.builder()
                .idHuesped(request.idHuesped())
                .idHabitaciones(request.idHabitaciones())
                .fechaIngreso(request.fechaIngreso())
                .fechaSalida(request.fechaSalida())
                .estadoReservacion(EstadoReservacion.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
    }

    @Override
    public ReservacionResponse entidadAResponse(Reservas entidad) {
        if (entidad == null) return null;

        return new ReservacionResponse(
                entidad.getId(),
                null,
                null,
                entidad.getFechaIngreso(),
                entidad.getFechaSalida(),
                entidad.getEstadoReservacion().getDescripcion());
    }

    public ReservacionResponse entidadAResponse(Reservas entidad, HuespedResponse huesped, HabitacionResponse habitacion) {
        if (entidad == null) return null;

        return new ReservacionResponse(
                entidad.getId(),
                this.huespedResponseADatosHuesped(huesped),
                this.habitacionResponseADatosHabitacion(habitacion),
                entidad.getFechaIngreso(),
                entidad.getFechaSalida(),
                entidad.getEstadoReservacion().getDescripcion());
    }

    private DatosHuesped huespedResponseADatosHuesped(HuespedResponse huesped){
        if(huesped == null) return null;

        return new DatosHuesped(
                huesped.nombre(),
                huesped.email(),
                huesped.telefono(),
                huesped.nacionalidad());
    }

    private DatosHabitacion habitacionResponseADatosHabitacion(HabitacionResponse habitacion){
        if(habitacion == null) return null;

        return new DatosHabitacion(
                habitacion.numeroHabitacion().toString(),
                habitacion.tipoHabitacion(),
                habitacion.precio().toString(),
                habitacion.capacidad().toString());
    }
}
