package com.proyectofinal.controllers;

import com.proyectofinal.common.controller.CommonController;
import com.proyectofinal.common.dto.HuespedRequest;
import com.proyectofinal.common.dto.HuespedResponse;
import com.proyectofinal.services.HuespedService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/huesped")
public class huespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService> {
    public huespedController(HuespedService service) {
        super(service);
    }

    @GetMapping("/id-huesped/{id}")
    public ResponseEntity<HuespedResponse>obtenerHuespedPorIdSinEstado(
            @PathVariable @Positive (message = "El id debe ser positivo") Long id){
        return ResponseEntity.ok(service.obtenerHuespedPorSinEstado(id));

    }
}
