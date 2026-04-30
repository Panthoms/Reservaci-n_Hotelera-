package com.proyectofinal.reservas.entities;

import com.proyectofinal.common.enums.EstadoRegistro;
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

    @Column(name = "ESTADO_REGISTRO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoRegistro estadoRegistro;


    public void actualizar(Long idHuesped, Long idHabitaciones, Date fechaIngreso, Date fechaSalida) {
        this.puedeActualizar();
        this.idHuesped = idHuesped;
        this.idHabitaciones = idHabitaciones;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    private void puedeActualizar(){
        if (!this.estadoReservacion.equals(EstadoReservacion.CONFIRMADA))
                throw new IllegalStateException("La reservacion solo se puede actualizar en estado "
                + EstadoReservacion.CONFIRMADA);
    }

    public void actualizarFechas(Date fechaSalida) {
        this.puedeActualizarFechas();
        this.fechaSalida = fechaSalida;
    }

    private void puedeActualizarFechas(){
        if (!this.estadoReservacion.equals(EstadoReservacion.EN_CURSO))
            throw new IllegalStateException("La reservacion solo se puede actualizar en estado "
                + EstadoReservacion.EN_CURSO);
    }

    public void actualizarEstadoReservacion(EstadoReservacion nuevoEstadoReservacion){

    }


}
