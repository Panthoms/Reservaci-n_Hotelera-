package com.proyectofinal.habitaciones.services;

import com.proyectofinal.common.clients.ReservacionClient;
import com.proyectofinal.common.dto.HabitacionRequest;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.common.enums.EstadoHabitacion;
import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import com.proyectofinal.habitaciones.entities.Habitaciones;
import com.proyectofinal.habitaciones.mappers.HabitacionMapoer;
import com.proyectofinal.habitaciones.repositories.HabitacionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HabitacionServiceImpl implements HabitacionService{

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapoer habitacionMapoer;
    private final ReservacionClient reservacionClient;

    @Override
    public List<HabitacionResponse> listar() {
    log.info("Listando las habitaciones activas");
    return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
            .map(habitacionMapoer::entidadAResponse).toList();
    }

    @Override
    public HabitacionResponse obtenerPorId(Long id) {
        return habitacionMapoer.entidadAResponse(obtenerHabitacionOExcepction(id));
    }

    @Override
    public HabitacionResponse optenerPorIdSinEstado(Long id) {
        log.info("Buscando habitaciones sin estado...");
        return habitacionMapoer.entidadAResponse(habitacionRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Habitacion no encontrada con el id: "+ id)
        ));
    }

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        if (request == null) return null;
        log.info("Registrando habitación...");
        validarNumeroUnico(request);

        Habitaciones habitaciones = habitacionMapoer.requestAEntidad(request);
        log.info("Habitacion {} registrado con éxito", request.numeroHabitacion());

        habitacionRepository.save(habitaciones);
        return  habitacionMapoer.entidadAResponse(habitaciones);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
       Habitaciones habitaciones = obtenerHabitacionOExcepction(id);
       log.info("Actualizando habitación con id: {}", id);

       habitacionTieneReservacionesAsignadas(id);

       validarNumeroUnicoId(request, id);

       habitaciones.actualizar(
               request.numeroHabitacion(),
               request.tipoHabitacion(),
               request.precio(),
               request.capacidad()
       );
        log.info("Habitacion con id: {} actualizazda con exito" , id);

        return habitacionMapoer.entidadAResponse(habitaciones);
    }

    @Override
    public void actualizarEstado(Long id, Long idEstadoHabitacion) {
        Habitaciones habitaciones = obtenerHabitacionOExcepction(id);
        log.info("Actualizando el estado de la habitación con id {}", id);
        EstadoHabitacion estadoHabitacion = EstadoHabitacion.obtenerHabitacionPorCodigo(idEstadoHabitacion);
        habitaciones.actualizarEstadoHabitacion(estadoHabitacion);
        log.info("Estado de la habitacion {} actualizada correctamente {}", id);
    }

    @Override
    public void eliminar(Long id) {
        Habitaciones habitaciones = obtenerHabitacionOExcepction(id);
        log.info("Eliminando la habitación con id {}", id);

        habitacionTieneReservacionesAsignadas(id);

        habitaciones.eliminar();
        log.info("Habitacion con id {} ha sido eliminada", id);
    }

    private Habitaciones obtenerHabitacionOExcepction(Long id){
        log.info("Buscando habitaciones activos con el id: {} ", id);
        return habitacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
                () -> new RecursoNoEncontradoException("habitacion activa no encontrada con el id: " + id)
        );
    }

    private void validarNumeroUnico(HabitacionRequest request){
        if(habitacionRepository.existsByNumeroHabitacionAndEstadoRegistro(request.numeroHabitacion(),
                EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe una habitación registrada con ese numero: "+ request.numeroHabitacion());
    }

    private void validarNumeroUnicoId(HabitacionRequest request, Long id){
        Habitaciones habitaciones = obtenerHabitacionOExcepction(id);

        if (habitaciones.getEstadoRegistro()==EstadoRegistro.ELIMINADO)
            return;;

        if (!habitaciones.getNumeroHabitacion().equals(request.numeroHabitacion())&&
            habitacionRepository.existsByNumeroHabitacionAndEstadoRegistroAndIdNot(
                    request.numeroHabitacion(), habitaciones.getEstadoRegistro(),id))
            throw new IllegalArgumentException("Ya exoste una habitacion con ese numero registrado");
    }

    private void habitacionTieneReservacionesAsignadas(Long id){
        reservacionClient.habitacionTieneReservacionesAsignadas(id);
    }


}
