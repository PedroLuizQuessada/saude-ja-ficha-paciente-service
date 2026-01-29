package com.example.saudejafichapacienteservice.gateways;

import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.exceptions.NaoEncontradoException;
import com.example.saudejafichapacienteservice.mappers.FichaPacienteMapper;

import java.util.Optional;

public class FichaPacienteGateway {

    private final FichaPacienteDataSource fichaPacienteDataSource;

    public FichaPacienteGateway(FichaPacienteDataSource fichaPacienteDataSource) {
        this.fichaPacienteDataSource = fichaPacienteDataSource;
    }

    public Long countByPaciente(Long pacienteId) {
        return fichaPacienteDataSource.countByPaciente(pacienteId);
    }

    public FichaPaciente saveFichaPaciente(FichaPacienteDto fichaPacienteDto) {
        FichaPacienteDto fichaPacienteDtoSalva = fichaPacienteDataSource.saveFichaPaciente(fichaPacienteDto);
        return FichaPacienteMapper.toEntidade(fichaPacienteDtoSalva);
    }

    public FichaPaciente getFichaPacienteByPacienteId(Long pacienteId) {
        Optional<FichaPacienteDto> optionalFichaPacienteDto = fichaPacienteDataSource.getFichaPacienteByPacienteId(pacienteId);
        if (optionalFichaPacienteDto.isEmpty())
            throw new NaoEncontradoException(String.format("Ficha de paciente não encontrada para o paciente %d.", pacienteId));
        return FichaPacienteMapper.toEntidade(optionalFichaPacienteDto.get());
    }

    public void deleteFichaPacienteById(Long id) {
        fichaPacienteDataSource.deleteFichaPacienteById(id);
    }
}
