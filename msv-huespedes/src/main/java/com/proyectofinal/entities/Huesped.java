package com.proyectofinal.entities;

import com.proyectofinal.common.enums.EstadoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter  @Setter
@Table(name = "HUESPED")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Huesped {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED")
    private Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

	@Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

	@Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

	@Column(name = "TELEFONO", unique = true, nullable = false, length = 10)
    private String telefono;

	@Column(name = "TIPO_DOCUMENTO", nullable = false, length = 100, unique = true)
    private String tipoDocumento;

	@Column(name = "NUMERO_DOCUMENTO", nullable = false, length = 100, unique = true)
    private String numeroDocumento;

	@Column(name = "NACIONALIDAD", nullable = false, length = 100)
    private String nacionalidad;

    @Column(name = "ESTADO_REGISTRO", columnDefinition = "VARCHAR2(1)")
    private EstadoRegistro estadoRegistro;

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String tipoDocumento, String numeroDocumento) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }


}
