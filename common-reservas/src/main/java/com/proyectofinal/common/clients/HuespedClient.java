package com.proyectofinal.common.clients;

import com.proyectofinal.common.dto.HuespedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-huespedes")
public interface HuespedClient {


    @GetMapping("/{id}")
    HuespedResponse obtenerPorId(@PathVariable Long id);

    @GetMapping("/id-huesped/{id}")
    HuespedResponse obtenerHuespedPorIdSinEstado(@PathVariable  Long id);
}
