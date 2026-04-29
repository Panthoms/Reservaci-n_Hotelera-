package com.proyectofinal.services;

import com.proyectofinal.common.dto.HuespedRequest;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.common.enums.EstadoRegistro;
import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import com.proyectofinal.entities.Huesped;
import com.proyectofinal.mappers.HuespedMapper;
import com.proyectofinal.repositories.HuespedRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HuespedServiceImpl implements HuespedService{

    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;


    @Override
    public List<HuespedResponse> listar() {
        return estadoHuesped(EstadoRegistro.ACTIVO);
    }

    @Override
    public HuespedResponse obtenerPorId(Long id) {
        return huespedMapper.entidadAResponse(obtenerHuespedActivoOException(id));
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        validarEmailPorEstado(request.email(), EstadoRegistro.ACTIVO);
        validarTelefonoPorEstado(request.telefono(), EstadoRegistro.ACTIVO);
        validarNumeroDocumentoUnica(request.numeroDocumento(),EstadoRegistro.ACTIVO);

        Huesped huesped = huespedMapper.requestAEntidad(request);

        huespedRepository.save(huesped);

        log.info("Nuevo Huesped {} registrado" , huesped.getNombre());
        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        Huesped huesped = obtenerHuespedActivoOException(id);

        validarActualizarUnicos(request, EstadoRegistro.ACTIVO,id);

        huesped.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                request.email(),
                request.telefono(),
                request.tipoDocumento(),
                request.numeroDocumento());

        log.info("Huesped actualizado con éxito: {})" + id);

        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando (lógicamente) huésped con id: {}", id);

        Huesped huesped = huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Huésped no encontrado"));

        huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        huespedRepository.save(huesped);
        log.info("Huésped con id: {} eliminado...", id);

    }

    private List<HuespedResponse> estadoHuesped(EstadoRegistro estados) {
        return huespedRepository.findByEstadoRegistro(estados)
                .stream()
                .map(huespedMapper::entidadAResponse)
                .toList();
    }

    private Huesped obtenerHuespedActivoOException(Long id){

        return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(()->
                new RecursoNoEncontradoException("Huesped no encontrado con el id "+ id));


    }

    private void validarEmailPorEstado(String email, EstadoRegistro estados) {
        log.info("validando email por estado...");
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(email, EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el emial: " + email);
    }

    private void validarNumeroDocumentoUnica(String numeroDocuemnto, EstadoRegistro estados) {
        log.info("validando email por estado...");
        if (huespedRepository.existsByNumeroDocumentoAndEstadoRegistro(numeroDocuemnto, EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el número de documento: " + numeroDocuemnto);
    }

    private void validarTelefonoPorEstado(String telefono, EstadoRegistro estados) {
        log.info("validando email por estado...");
        if (huespedRepository.existsByTelefonoAndEstadoRegistro(telefono, EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un medico con el telefono: " + telefono);
    }

    private void validarActualizarUnicos(HuespedRequest request, EstadoRegistro estados, Long id) {
        log.info("validando actualizacion por estado...");
        if (huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistroAndIdNot(request.email(), EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huesped con el emial: " + request.email());

        if (huespedRepository.existsByTelefonoAndEstadoRegistroAndIdNot(request.telefono(), EstadoRegistro.ACTIVO, id))
            throw new IllegalArgumentException("Ya existe un huesped con el telefono: " + request.telefono());

        if (huespedRepository.existsByTipoDocumentoAndIdNotAndEstadoRegistro(request.tipoDocumento(),id , EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el tipo de documento: " + request.telefono());

        if (huespedRepository.existsByNumeroDocumentoAndIdNotAndEstadoRegistro(request.tipoDocumento(),id , EstadoRegistro.ACTIVO))
            throw new IllegalArgumentException("Ya existe un huesped con el número de documento: " + request.telefono());

    }

    @Override
    public HuespedResponse obtenerHuespedPorSinEstado(Long id) {
        return null;
    }
}
