package com.example.saudejafichapacienteservice.datasources;

import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.dtos.PacienteIdDtoPage;

import java.util.Optional;

public interface FichaPacienteDataSource {
    Long countByPaciente(Long pacienteId);
    FichaPacienteDto saveFichaPaciente(FichaPacienteDto fichaPacienteDto);
    Optional<FichaPacienteDto> getFichaPacienteByPacienteId(Long pacienteId);
    void deleteFichaPacienteById(Long id);
    PacienteIdDtoPage getPacientesHipertensosId(int page, int size);
}
