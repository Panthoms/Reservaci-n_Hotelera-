package com.proyectofinal.common.enums;

import com.proyectofinal.common.exceptions.RecursoNoEncontradoException;
import com.proyectofinal.common.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EstadoRegistro {
	ACTIVO( "ACTIVO"),
	ELIMINADO("ELIMINADO");

	private final String description;

	public static EstadoRegistro encontrarPorDescripcion(String description) {
		String normalizedDescription = StringCustomUtils.quitarAcentos(description);
		for (EstadoRegistro estado : values()) {
			if (StringCustomUtils.quitarAcentos(estado.description).equalsIgnoreCase(normalizedDescription.trim())) {
				return estado;
			}
		}
		throw new RecursoNoEncontradoException("Register state not found by description:" + description);
	}

}
