package com.example.saudejafichapacienteservice.controllers;

import com.example.saudejafichapacienteservice.datasources.AutenticacaoDataSource;
import com.example.saudejafichapacienteservice.gateways.AutenticacaoGateway;
import com.example.saudejafichapacienteservice.usecases.GerarTokenServicoUseCase;

public class AutenticacaoController {

    private final AutenticacaoDataSource autenticacaoDataSource;

    public AutenticacaoController(AutenticacaoDataSource autenticacaoDataSource) {
        this.autenticacaoDataSource = autenticacaoDataSource;
    }

    public String gerarTokenServico(String audiencia) {
        AutenticacaoGateway autenticacaoGateway = new AutenticacaoGateway(autenticacaoDataSource);
        GerarTokenServicoUseCase useCase = new GerarTokenServicoUseCase(autenticacaoGateway);

        return useCase.executar(audiencia);
    }
}
