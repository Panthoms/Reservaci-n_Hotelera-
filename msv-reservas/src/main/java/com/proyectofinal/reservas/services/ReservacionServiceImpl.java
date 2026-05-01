package com.proyectofinal.reservas.services;

import com.proyectofinal.common.clients.HabitacionClient;
import com.proyectofinal.common.clients.HuespedClient;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.common.enums.EstadoHabitacion;
import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.common.exceptions.EntidadRelacionadaException;
import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import com.proyectofinal.reservas.dto.ReservacionRequest;
import com.proyectofinal.reservas.dto.ReservacionResponse;
import com.proyectofinal.reservas.entities.Reservas;
import com.proyectofinal.reservas.enums.EstadoReservacion;
import com.proyectofinal.reservas.mappers.ReservacionMapper;
import com.proyectofinal.reservas.repositories.ReservacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ReservacionServiceImpl implements ReservacionService{

    private final ReservacionRepository reservacionRepository;
    private final ReservacionMapper reservacionMapper;
    private final HuespedClient huespedClient;
    private final HabitacionClient habitacionClient;
    private final List<EstadoReservacion> ESTADOS_REGISTROS_ASIGNACION = List.of(EstadoReservacion.CONFIRMADA, EstadoReservacion.EN_CURSO);

    @Override
    public List<ReservacionResponse> listar() {
        log.info("Listando las reservaciones confirmadas");
        return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(reservacion ->
                        reservacionMapper.entidadAResponse(
                                reservacion,
                                obtenerHuespedSinEstado(reservacion.getIdHuesped()),
                                obtenerHabitacionActivo(reservacion.getIdHabitaciones())
                        )).toList();
    }

    @Override
    public ReservacionResponse obtenerPorId(Long id) {
        log.info("Buscando reservacion con id {}", id);
        Reservas reservas = obtenerReservacionOException(id);
        return  reservacionMapper.entidadAResponse(reservas,
                obtenerHuespedSinEstado(reservas.getIdHuesped()),
                obtenerHabitacionSinEstado(reservas.getIdHabitaciones()));
    }

    @Override
    public void huespedTieneReservacionesAsignadas(Long idAHuesped) {
        boolean tieneReservaciones = reservacionRepository
                .existsByIdHuespedAndEstadoRegistroAndEstadoReservacionIn(
                        idAHuesped,
                        EstadoRegistro.ACTIVO,
                        ESTADOS_REGISTROS_ASIGNACION);
        if (tieneReservaciones)
            throw new EntidadRelacionadaException(
                    "No se puede modicar el huesped ya que tiene reservacion  con estado: "
            + ESTADOS_REGISTROS_ASIGNACION);
    }

    @Override
    public void habitacionTieneReservacionAsignadas(Long idHabitacion) {
        boolean tieneReservaciones = reservacionRepository
                .existsByIdHabitacionesAndEstadoRegistroAndEstadoReservacionIn(
                        idHabitacion,
                        EstadoRegistro.ACTIVO,
                        ESTADOS_REGISTROS_ASIGNACION);

        if (tieneReservaciones)
            throw new EntidadRelacionadaException(
                    "No se puede modicar el huesped ya que tiene reservacion  con estado: "
                            + ESTADOS_REGISTROS_ASIGNACION);
    }

    @Override
    public ReservacionResponse registrar(ReservacionRequest request) {
        log.info("Registrando nueva reserva... ");
        HuespedResponse huesped = obtenerHuespedActivo(request.idHuesped());
        HabitacionResponse habitacion = obtenerHabitacionActivo(request.idHabitaciones());
        validarEstadoHabitacion(habitacion);

        validarHuespedTieneReservacionesAsignadas(request.idHuesped());
        validarHabitacionTieneReservacionesAsignadas(request.idHabitaciones());
        validarFechaSalida(request);
        Reservas reservas = reservacionRepository.save(reservacionMapper.requestAEntidad(request));
        cambiarEstadoHbitacionSegunEstadoReservacion(reservas.getIdHabitaciones(),reservas.getEstadoReservacion());

        return reservacionMapper.entidadAResponse(reservas, huesped, habitacion);
    }

    @Override
    public void actulizarEstadoReservacion(Long idReservas, Long idEstadoReservacion) {
        Reservas reservas = obtenerReservacionOException(idReservas);
        log.info("Actualizando el estado de reservacion con id {} ", idReservas);

        EstadoReservacion estadoReservacion = EstadoReservacion.obtenerDisponibilidadPorCodigo(idEstadoReservacion);

        reservas.actualizarEstadoReservacion(estadoReservacion);

        cambiarEstadoHbitacionSegunEstadoReservacion(reservas.getIdHabitaciones(), reservas.getEstadoReservacion());
        log.info("Estado de la reservacion {} actualizado correctamente ", reservas.getId());
    }

    @Override
    public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
        Reservas reservas = obtenerReservacionOException(id);
        log.info("actualizando reservacion con id {} ", id);

        EstadoReservacion estado = reservas.getEstadoReservacion();
        if (estado == EstadoReservacion.FINALIZADA || estado == EstadoReservacion.CANCELADA)
            throw new IllegalStateException("No se puede actualizar una reservación " + estado);

        validarFechaSalida(request);

        if(reservas.getEstadoReservacion() == EstadoReservacion.EN_CURSO){

            if (!reservas.getIdHuesped().equals(request.idHuesped())
                    || !reservas.getIdHabitaciones().equals(request.idHabitaciones())
                    || !reservas.getFechaIngreso().equals(request.fechaIngreso()))
                throw new IllegalStateException(
                        "Una reservación EN_CURSO solo permite modificar la fecha de salida");

            reservas.actualizarFechas(
                    request.fechaSalida());
            return reservacionMapper.entidadAResponse(reservas,
                    obtenerHuespedActivo(reservas.getIdHuesped()),
                    obtenerHabitacionActivo(reservas.getIdHabitaciones()));
        }

        Long idHabitacionAnterior = reservas.getIdHabitaciones();

        HuespedResponse huespedNuevo = reservas.getIdHuesped().equals(request.idHuesped())
                ? null : obtenerHuespedActivo(request.idHuesped());
        HabitacionResponse habitacionNueva = reservas.getIdHabitaciones().equals(request.idHabitaciones())
                ? null : obtenerHabitacionActivo(request.idHabitaciones());

        if(huespedNuevo != null)
            validarHuespedTieneReservacionesAsignadas(request.idHuesped());

        if (habitacionNueva != null)
            validarEstadoHabitacion(habitacionNueva);

        reservas.actualizar(
                request.idHuesped(),
                request.idHabitaciones(),
                request.fechaIngreso(),
                request.fechaSalida()
        );

        if (habitacionNueva != null) {
            cambiarEstadoHabitacion(idHabitacionAnterior,
                    EstadoHabitacion.DISPONIBLE.getCodigo());
            cambiarEstadoHbitacionSegunEstadoReservacion(reservas.getIdHabitaciones(),
                    reservas.getEstadoReservacion());
        }
        log.info("Reservacion actualizada {} ", reservas.getId());

        return reservacionMapper.entidadAResponse(reservas,
                obtenerHuespedActivo(reservas.getIdHuesped()),
                obtenerHabitacionActivo(reservas.getIdHabitaciones()));
    }

    @Override
    public void eliminar(Long id) {
        Reservas reservas = obtenerReservacionOException(id);
        log.info("Eliminando la reservación con id {}", id);

        reservas.eliminar();

        if (reservas.getEstadoReservacion() == EstadoReservacion.CONFIRMADA) {
            cambiarEstadoHabitacion(
                    reservas.getIdHabitaciones(),
                    EstadoHabitacion.DISPONIBLE.getCodigo()
            );
        }

        log.info("La reservación con id {} ha sido eliminada exitosamente", id);
    }

    private Reservas obtenerReservacionOException(Long id){
        log.info("Buscando reservacion con id {} ", id);
        return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
                () -> new RecursoNoEncontradoException("Reservacion no encontrada con el id: " + id));
    }

    private HuespedResponse obtenerHuespedSinEstado(Long idHuesped){
        log.info("Listnado huespedes sin estado");
        return  huespedClient.obtenerHuespedPorIdSinEstado(idHuesped);
    }

    private HuespedResponse obtenerHuespedActivo(Long idHuesped){
        log.info("Listando huespedes activos");
        return  huespedClient.obtenerPorId(idHuesped);
    }

    private HabitacionResponse obtenerHabitacionSinEstado(Long idHabitacion){
        log.info("Listnado habitaciones sin estado");
        return  habitacionClient.obtenerHabitacionSinEstado(idHabitacion);
    }

    private HabitacionResponse obtenerHabitacionActivo(Long idHabitacion){
        log.info("Listando habitaciones activos");
        return  habitacionClient.obtenerPorId(idHabitacion);
    }

    private void validarEstadoHabitacion(HabitacionResponse habitacion){
        log.info("Validando si la habitacion se encuentra en estado: {}", EstadoHabitacion.DISPONIBLE);
        if(!EstadoHabitacion.DISPONIBLE.getDescripcion().equalsIgnoreCase(habitacion.estadoHabitacion()))
            throw new IllegalStateException("La habitacion no se encuentra en estado: " + EstadoHabitacion.DISPONIBLE);
    }

    private void validarHuespedTieneReservacionesAsignadas(Long idHuesped){
        if (reservacionRepository.existsByIdHuespedAndEstadoRegistroAndEstadoReservacionIn(
                idHuesped, EstadoRegistro.ACTIVO, ESTADOS_REGISTROS_ASIGNACION))
            throw new IllegalArgumentException(
                    "No se puede reservar la habitacion ya que el huesped solo puede tener una reservacion activa con los estados: "
                    + ESTADOS_REGISTROS_ASIGNACION);
    }

    private void validarHabitacionTieneReservacionesAsignadas(Long idHabitacion){
        if (reservacionRepository.existsByIdHabitacionesAndEstadoRegistroAndEstadoReservacionIn(
                idHabitacion, EstadoRegistro.ACTIVO, ESTADOS_REGISTROS_ASIGNACION))
            throw new IllegalArgumentException(
                    "No se puede reservar la habitacion ya que el huesped solo puede tener una reservacion activa con los estados: "
                            + ESTADOS_REGISTROS_ASIGNACION);
    }

    private void cambiarEstadoHbitacionSegunEstadoReservacion(Long idHabitacion, EstadoReservacion estadoReservacion) {
        switch (estadoReservacion) {
            case CONFIRMADA, EN_CURSO ->
                    cambiarEstadoHabitacion(idHabitacion, EstadoHabitacion.OCUPADA.getCodigo());

            case FINALIZADA, CANCELADA ->
                    cambiarEstadoHabitacion(idHabitacion, EstadoHabitacion.DISPONIBLE.getCodigo());
        }
    }

    private void cambiarEstadoHabitacion(Long idHabitacion, Long idEstadoHabitacion){
        log.info("Actualizando estado de la habitacion con id {} a id {}",
                idHabitacion,EstadoHabitacion.obtenerHabitaciomnPorDescripcion(idEstadoHabitacion));

        habitacionClient.actualizarEstadoHabitacion(idHabitacion, idEstadoHabitacion);
    }

    private void validarFechaSalida(ReservacionRequest request){
        if (!request.fechaSalida().after(request.fechaIngreso()))
            throw new IllegalArgumentException(
                    "La fecha de salida debe ser mayor a la fecha de ingreso.");
    }


}
