package com.example.saudejafichapacienteservice.mappers;

import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import dtos.requests.CriarFichaPacienteRequest;
import dtos.responses.FichaPacienteResponse;

public class FichaPacienteMapper {

    private FichaPacienteMapper() {}

    public static FichaPaciente toEntidade(CriarFichaPacienteRequest criarFichaPacienteRequest) {
        return new FichaPaciente(null, criarFichaPacienteRequest.paciente(), criarFichaPacienteRequest.dataNascimento(),
                criarFichaPacienteRequest.genero(), criarFichaPacienteRequest.hipertensao(), criarFichaPacienteRequest.diabetes(),
                criarFichaPacienteRequest.aids(), criarFichaPacienteRequest.observacoes());
    }

    public static FichaPaciente toEntidade(FichaPacienteDto fichaPacienteDto) {
        return new FichaPaciente(fichaPacienteDto.id(), fichaPacienteDto.paciente(), fichaPacienteDto.dataNascimento(),
                fichaPacienteDto.genero(), fichaPacienteDto.hipertensao(), fichaPacienteDto.diabetes(), fichaPacienteDto.aids(),
                fichaPacienteDto.observacoes());
    }

    public static FichaPacienteDto toDto(FichaPaciente fichaPaciente) {
        return new FichaPacienteDto(fichaPaciente.getId(), fichaPaciente.getPaciente(), fichaPaciente.getDataNascimento(),
                fichaPaciente.getGenero(), fichaPaciente.getHipertensao(), fichaPaciente.getDiabetes(), fichaPaciente.getAids(),
                fichaPaciente.getObservacoes());
    }

    public static FichaPacienteResponse toResponse(FichaPaciente fichaPaciente) {
        return new FichaPacienteResponse(fichaPaciente.getId(), fichaPaciente.getPaciente(), fichaPaciente.getDataNascimento(),
                fichaPaciente.getGenero(), fichaPaciente.getHipertensao(), fichaPaciente.getDiabetes(), fichaPaciente.getAids(),
                fichaPaciente.getObservacoes());
    }
}
