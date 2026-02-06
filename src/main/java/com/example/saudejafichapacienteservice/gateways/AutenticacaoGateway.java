package com.example.saudejafichapacienteservice.gateways;

import com.example.saudejafichapacienteservice.datasources.AutenticacaoDataSource;

public class AutenticacaoGateway {

    private final AutenticacaoDataSource autenticacaoDataSource;

    public AutenticacaoGateway(AutenticacaoDataSource autenticacaoDataSource) {
        this.autenticacaoDataSource = autenticacaoDataSource;
    }

    public String gerarTokenServico(String audiencia) {
        return autenticacaoDataSource.gerarTokenServico(audiencia);
    }
}
