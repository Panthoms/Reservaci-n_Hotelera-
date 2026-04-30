package com.proyectofinal.auth.services;

import com.proyectofinal.auth.dto.LoginRequest;
import com.proyectofinal.auth.dto.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}

