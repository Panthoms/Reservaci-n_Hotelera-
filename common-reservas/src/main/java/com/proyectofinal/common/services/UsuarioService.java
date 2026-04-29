package com.proyectofinal.common.services;

import com.proyectofinal.common.dto.UsuarioRequest;
import com.proyectofinal.common.dto.UsuarioResponse;

import java.util.Set;

public interface UsuarioService {
    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}
