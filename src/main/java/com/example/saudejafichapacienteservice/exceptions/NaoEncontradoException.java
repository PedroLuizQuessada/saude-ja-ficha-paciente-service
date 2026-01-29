package com.example.saudejafichapacienteservice.exceptions;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException(String message) {
        super(message);
    }
}
