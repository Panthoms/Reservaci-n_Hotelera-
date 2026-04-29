package com.proyectofinal.services;

import com.proyectofinal.common.dto.HuespedRequest;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.common.services.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse> {
    HuespedResponse obtenerHuespedPorSinEstado(Long id);
}
