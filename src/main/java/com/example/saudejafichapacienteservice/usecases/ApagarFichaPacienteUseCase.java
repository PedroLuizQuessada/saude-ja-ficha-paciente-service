package com.example.saudejafichapacienteservice.usecases;

import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;

public class ApagarFichaPacienteUseCase {

    private final FichaPacienteGateway fichaPacienteGateway;

    public ApagarFichaPacienteUseCase(FichaPacienteGateway fichaPacienteGateway) {
        this.fichaPacienteGateway = fichaPacienteGateway;
    }

    public void executar(Long pacienteId) {
        FichaPaciente fichaPaciente = fichaPacienteGateway.getFichaPacienteByPacienteId(pacienteId);
        fichaPacienteGateway.deleteFichaPacienteById(fichaPaciente.getPaciente());
    }
}
