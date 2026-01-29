package com.example.saudejafichapacienteservice.controllers;

import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;
import com.example.saudejafichapacienteservice.mappers.FichaPacienteMapper;
import com.example.saudejafichapacienteservice.usecases.ApagarFichaPacienteUseCase;
import com.example.saudejafichapacienteservice.usecases.AtualizarFichaPacienteUseCase;
import com.example.saudejafichapacienteservice.usecases.CriarFichaPacienteUseCase;
import com.example.saudejafichapacienteservice.usecases.GetFichaPacienteUseCase;
import dtos.requests.AtualizarFichaPacienteRequest;
import dtos.requests.CriarFichaPacienteRequest;
import dtos.responses.FichaPacienteResponse;

public class FichaPacienteController {

    private final FichaPacienteDataSource fichaPacienteDataSource;

    public FichaPacienteController(FichaPacienteDataSource fichaPacienteDataSource) {
        this.fichaPacienteDataSource = fichaPacienteDataSource;
    }

    public FichaPacienteResponse criarFichaPaciente(CriarFichaPacienteRequest criarFichaPacienteRequest) {
        FichaPacienteGateway fichaPacienteGateway = new FichaPacienteGateway(fichaPacienteDataSource);
        CriarFichaPacienteUseCase useCase = new CriarFichaPacienteUseCase(fichaPacienteGateway);

        FichaPaciente fichaPaciente = useCase.executar(criarFichaPacienteRequest);

        return FichaPacienteMapper.toResponse(fichaPaciente);
    }

    public FichaPacienteResponse getFichaPaciente(Long pacienteId) {
        FichaPacienteGateway fichaPacienteGateway = new FichaPacienteGateway(fichaPacienteDataSource);
        GetFichaPacienteUseCase useCase = new GetFichaPacienteUseCase(fichaPacienteGateway);

        FichaPaciente fichaPaciente = useCase.executar(pacienteId);

        return FichaPacienteMapper.toResponse(fichaPaciente);
    }

    public FichaPacienteResponse atualizarFichaPaciente(AtualizarFichaPacienteRequest atualizarFichaPacienteRequest) {
        FichaPacienteGateway fichaPacienteGateway = new FichaPacienteGateway(fichaPacienteDataSource);
        AtualizarFichaPacienteUseCase useCase = new AtualizarFichaPacienteUseCase(fichaPacienteGateway);

        FichaPaciente fichaPaciente = useCase.executar(atualizarFichaPacienteRequest);

        return FichaPacienteMapper.toResponse(fichaPaciente);
    }

    public void apagarFichaPaciente(Long pacienteId) {
        FichaPacienteGateway fichaPacienteGateway = new FichaPacienteGateway(fichaPacienteDataSource);
        ApagarFichaPacienteUseCase useCase = new ApagarFichaPacienteUseCase(fichaPacienteGateway);

        useCase.executar(pacienteId);
    }
}
