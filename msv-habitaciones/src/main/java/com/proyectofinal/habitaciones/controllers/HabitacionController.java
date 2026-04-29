package com.proyectofinal.habitaciones.controllers;

import com.proyectofinal.common.controller.CommonController;
import com.proyectofinal.common.dto.HabitacionRequest;
import com.proyectofinal.common.dto.HabitacionResponse;
import com.proyectofinal.habitaciones.services.HabitacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService> {
    public HabitacionController(HabitacionService service) {
        super(service);
    }

    @GetMapping("/id-habitacion/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionSinEstado(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id
    ){
        return ResponseEntity.ok(service.optenerPorIdSinEstado(id));
    }

    @PutMapping("/{id}/estado/{idEstadoHabitacion}")
    public ResponseEntity<Void> actualizarEstadoHabitacion(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @PathVariable @Positive(message = "El id debe ser positivo") Long idEstadoHabitacion
    ){
        service.actualizarEstado(id,idEstadoHabitacion);
        return ResponseEntity.noContent().build();
    }


}
