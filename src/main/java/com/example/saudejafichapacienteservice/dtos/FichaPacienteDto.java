package com.example.saudejafichapacienteservice.dtos;

import enums.GeneroEnum;

import java.time.LocalDate;

public record FichaPacienteDto(Long id, Long paciente, LocalDate dataNascimento, GeneroEnum genero, Boolean hipertensao, Boolean diabetes, Boolean aids, String observacoes) {
}
