package com.proyectofinal.auth.services;

import java.util.Set;

import com.josealberto.auth.dto.UsuarioRequest;
import com.josealberto.auth.dto.UsuarioResponse;
import com.proyectofinal.auth.entities.Rol;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse actualizar(String username, UsuarioRequest request);

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

