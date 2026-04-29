package com.proyectofinal.reservas.entities;

import com.proyectofinal.reservas.enums.EstadoReservacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVACIONES")
@Builder
public class Reservas {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVACION")
    private Long id;

    @Column(name = "ID_HUESPED", nullable = false)
    private Long idHuesped;

    @Column(name = "ID_HABITACIONES", nullable = false)
    private Long idHabitaciones;

    @Column(name = "FECHA_INGRESO", nullable = false)
    Date fechaIngreso;

    @Column(name = "FECHA_SALIDA", nullable = false)
    Date fechaSalida;

    @Column(name = "ESTADO_RESERVACION", nullable = false)
    @Enumerated(EnumType.STRING)
    EstadoReservacion estadoReservacion;

}
