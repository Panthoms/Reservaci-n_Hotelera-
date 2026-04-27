package com.proyectofinal.habitaciones.entities;

import java.math.BigDecimal;

import com.proyectofinal.common.enums.EstadoHabitacion;
import com.proyectofinal.common.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HABITACION")
@Builder
public class Habitaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_HABITACION")
	private Long id;
	
	@Column(name = "NUMERO_HABITACION", nullable = false, unique = true)
	private Integer numeroHabitacion;
	
	@Column(name = "TIPO", nullable = false, length = 20)
	private String tipoHabitacion;
	
	@Column(name = "PRECIO", nullable = false)
	private BigDecimal precio;

	@Column(name = "CAPACIDAD")
	private Integer capacidad;

	@Column(name = "ESTADO_HABITACION", nullable = false)
	private EstadoHabitacion estadoHabitacion;

	@Column(name = "ESTADO_REGISTRO", nullable = false)
	private EstadoRegistro estadoRegistro;


	
	
	
	
}
