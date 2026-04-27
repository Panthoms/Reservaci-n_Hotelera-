package com.proyectofinal.huespedes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Geters @Setter
@Table(name = "HUESPED")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Huesped {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 50)
    String nombre;

	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    String apellidoPaterno;

	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    String apellidoMaterno;

	@Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    String email;

	@Column(name = "TELEFONO", unique = true, nullable = false, length = 10)
    String telefono;

	@Column(name = "TIPO_DOCUMENTO", nullable = false, length = 100, unique = true)
    String tipoDocumento;

	@Column(name = "NUMERO_DOCUMENTO", nullable = false, length = 100, unique = true)
    String numeroDocumento;

	@Column(name = "NUMERO_DOCUMENTO", nullable = false, length = 100)
    String nacionalidad;
}
