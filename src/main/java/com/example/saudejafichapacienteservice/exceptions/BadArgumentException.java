package com.example.saudejafichapacienteservice.exceptions;

public class BadArgumentException extends RuntimeException {
    public BadArgumentException(String message) {
        super(message);
    }
}
