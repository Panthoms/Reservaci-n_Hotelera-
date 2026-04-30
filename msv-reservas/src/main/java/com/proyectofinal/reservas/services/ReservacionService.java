package com.proyectofinal.reservas.services;

import com.proyectofinal.common.services.CrudService;
import com.proyectofinal.reservas.dto.ReservacionRequest;
import com.proyectofinal.reservas.dto.ReservacionResponse;

public interface ReservacionService extends CrudService<ReservacionRequest,ReservacionResponse> {

    void huespedTieneReservacionesAsignadas(Long idAHuesped);

    void habitacionTieneReservacionAsignadas(Long idHabitacion);

    void avtulizarEstadoReservacion(Long idResercas, Long idEstadoReservacion);



}
