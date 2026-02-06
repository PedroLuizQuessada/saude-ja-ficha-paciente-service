package com.example.saudejafichapacienteservice.entidades;

import com.example.saudejafichapacienteservice.exceptions.BadArgumentException;
import dtos.abstrato.Page;

import java.util.List;
import java.util.Objects;

public class PacienteIdPage extends Page<Long> {
    public PacienteIdPage(int page, int size, List<Long> content) {
        super(page, size, content);
        validarPacienteId(content);
    }

    private void validarPacienteId(List<Long> content) {
        content.forEach(pacienteId -> {
            if (Objects.isNull(pacienteId)) {
                throw new BadArgumentException("O paciente deve possuir um ID.");
            }
        });
    }
}
