package com.proyectofinal.auth.services;

import java.util.Set;

import com.proyectofinal.auth.dto.UsuarioRequest;
import com.proyectofinal.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse actualizar(String username, UsuarioRequest request);

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);

    UsuarioResponse actualizar(String username, UsuarioRequest request);

}

