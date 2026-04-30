package com.proyectofinal.auth.services;

import java.util.Set;

import com.proyectofinal.auth.dto.UsuarioRequest;
import com.proyectofinal.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

