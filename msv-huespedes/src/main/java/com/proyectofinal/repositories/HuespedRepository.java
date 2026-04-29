package com.proyectofinal.repositories;

import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.entities.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    List<Huesped> findByEstadoRegistro(EstadoRegistro estados);
    boolean existsByEmailIgnoreCaseAndEstadoRegistro(String emial, EstadoRegistro estados);
    boolean existsByNumeroDocumentoAndEstadoRegistro(String cedulaProfesional, EstadoRegistro estado);
    boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estados);
    boolean existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(String email, EstadoRegistro estados, Long id);
    boolean existsByTelefonoAndEstadoRegistroAndIdNot(String telefono, EstadoRegistro estados, Long id);
    boolean existsByTipoDocumentoAndIdNotAndEstadoRegistro(String tipoDocumento,Long id ,EstadoRegistro estado);

    boolean existsByNumeroDocumentoAndIdNotAndEstadoRegistro(String numeroDocumento,Long id ,EstadoRegistro estado);
}
