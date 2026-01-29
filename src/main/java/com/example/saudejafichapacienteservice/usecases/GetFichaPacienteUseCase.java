package com.example.saudejafichapacienteservice.usecases;

import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;

public class GetFichaPacienteUseCase {
    private final FichaPacienteGateway fichaPacienteGateway;

    public GetFichaPacienteUseCase(FichaPacienteGateway fichaPacienteGateway) {
        this.fichaPacienteGateway = fichaPacienteGateway;
    }

    public FichaPaciente executar(Long pacienteId) {
        return fichaPacienteGateway.getFichaPacienteByPacienteId(pacienteId);
    }
}
