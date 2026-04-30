package com.proyectofinal.habitaciones.repositories;

import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.habitaciones.entities.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitaciones, Long> {
    List<Habitaciones> findByEstadoRegistro(EstadoRegistro estadoRegistro);

    Optional<Habitaciones> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);

    boolean existsByNumeroHabitacionAndEstadoRegistro(Integer numeroHabitacion, EstadoRegistro estadoRegistro);

    boolean existsByNumeroHabitacionAndEstadoRegistroAndIdNot(
            Integer numeroHabitacion, EstadoRegistro estadoRegistro, Long id);

}
