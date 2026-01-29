package com.example.saudejafichapacienteservice.infrastructure.exceptions;

public class BadJpaArgumentException extends RuntimeException {
    public BadJpaArgumentException(String message) {
        super(message);
    }
}
