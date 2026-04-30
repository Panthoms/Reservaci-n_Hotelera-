package com.proyectofinal.common.clients;

import com.proyectofinal.common.dto.HabitacionResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msv-habitaciones")
public interface HabitacionClient {

    @GetMapping("/{id}")
    HabitacionResponse obtenerPorId(@PathVariable Long id);

    @GetMapping("/id-habitacion/{id}")
    HabitacionResponse obtenerHabitacionSinEstado(@PathVariable Long id);

    @PutMapping("/{id}/estado/{idEstadoHabitacion}")
    Void actualizarEstadoHabitacion(
            @PathVariable Long id,
            @PathVariable Long idEstadoHabitacion);



}
