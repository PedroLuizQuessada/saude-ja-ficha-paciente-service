package com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.mappers;

import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.models.FichaPacienteJpa;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jpa")
public class FichaPacienteJpaDtoMapper {

    public FichaPacienteJpa toJpa(FichaPacienteDto fichaPacienteDto) {
        return new  FichaPacienteJpa(fichaPacienteDto.id(), fichaPacienteDto.paciente(), fichaPacienteDto.dataNascimento(),
                fichaPacienteDto.genero(), fichaPacienteDto.hipertensao(), fichaPacienteDto.diabetes(), fichaPacienteDto.aids(), fichaPacienteDto.observacoes());
    }

    public FichaPacienteDto toDto(FichaPacienteJpa fichaPacienteJpa) {
        return new  FichaPacienteDto(fichaPacienteJpa.getId(), fichaPacienteJpa.getPaciente(), fichaPacienteJpa.getDataNascimento(),
                fichaPacienteJpa.getGenero(), fichaPacienteJpa.getHipertensao(), fichaPacienteJpa.getDiabetes(),
                fichaPacienteJpa.getAids(), fichaPacienteJpa.getObservacoes());
    }

}
