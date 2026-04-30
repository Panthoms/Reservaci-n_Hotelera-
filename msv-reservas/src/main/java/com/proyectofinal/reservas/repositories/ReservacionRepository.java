package com.proyectofinal.reservas.repositories;

import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.reservas.entities.Reservas;
import com.proyectofinal.reservas.enums.EstadoReservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservas,Long> {
    List<Reservas> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Reservas> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByIdHuespedAndEstadoRegistroAndEstadoReservacionIn(Long idHuesped,
                                                                     EstadoRegistro estadoRegistro, Collection<EstadoReservacion> estadoReservacions);

    boolean existsByIdHabitacionesAndEstadoRegistroAndEstadoReservacionIn(Long idHabitaciones,
                                                                          EstadoRegistro estadoRegistro, Collection<EstadoReservacion> estadoReservacions);
}


