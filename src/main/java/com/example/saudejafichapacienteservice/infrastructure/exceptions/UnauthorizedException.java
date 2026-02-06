package com.example.saudejafichapacienteservice.infrastructure.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Usuário ou senha incorretos");
    }
}
