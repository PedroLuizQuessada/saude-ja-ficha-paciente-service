package com.example.saudejafichapacienteservice.usecases;

import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.exceptions.BadArgumentException;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;
import com.example.saudejafichapacienteservice.mappers.FichaPacienteMapper;
import dtos.requests.CriarFichaPacienteRequest;

public class CriarFichaPacienteUseCase {

    private final FichaPacienteGateway fichaPacienteGateway;

    public CriarFichaPacienteUseCase(FichaPacienteGateway fichaPacienteGateway) {
        this.fichaPacienteGateway = fichaPacienteGateway;
    }

    public FichaPaciente executar(CriarFichaPacienteRequest criarFichaPacienteRequest) {
        FichaPaciente fichaPaciente = FichaPacienteMapper.toEntidade(criarFichaPacienteRequest);

        if (fichaPacienteGateway.countByPaciente(criarFichaPacienteRequest.paciente()) > 0) {
            throw new BadArgumentException("Paciente já possui uma ficha.");
        }

        return fichaPacienteGateway.saveFichaPaciente(FichaPacienteMapper.toDto(fichaPaciente));
    }
}
