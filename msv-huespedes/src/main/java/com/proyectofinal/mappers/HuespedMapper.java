package com.proyectofinal.mappers;

import com.proyectofinal.common.dto.HuespedRequest;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.common.mappers.CommonMapper;
import com.proyectofinal.entities.Huesped;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {
    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        if(request == null) return null;

        return Huesped.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .email(request.email())
                .telefono(request.telefono())
                .tipoDocumento(request.tipoDocumento())
                .numeroDocumento(request.numeroDocumento())
                .nacionalidad(request.nacionalidad())
                .build();
    }

    @Override
    public HuespedResponse entidadAResponse(Huesped entidad) {

        if (entidad == null) return null;

        return new HuespedResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                entidad.getTipoDocumento(),
                entidad.getNumeroDocumento(),
                entidad.getNacionalidad());
    }
}
