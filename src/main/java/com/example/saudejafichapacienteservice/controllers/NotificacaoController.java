package com.example.saudejafichapacienteservice.controllers;

import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.datasources.NotificacaoDataSource;
import com.example.saudejafichapacienteservice.datasources.UsuarioDataSource;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;
import com.example.saudejafichapacienteservice.gateways.NotificacaoGateway;
import com.example.saudejafichapacienteservice.gateways.UsuarioGateway;
import com.example.saudejafichapacienteservice.usecases.NotificarPacientesHipertensosUseCase;

public class NotificacaoController {

    private final FichaPacienteDataSource fichaPacienteDataSource;
    private final UsuarioDataSource usuarioDataSource;
    private final NotificacaoDataSource notificacaoDataSource;

    public NotificacaoController(FichaPacienteDataSource fichaPacienteDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.fichaPacienteDataSource = fichaPacienteDataSource;
        this.usuarioDataSource = usuarioDataSource;
        this.notificacaoDataSource = notificacaoDataSource;
    }

    public void notificarPacientesHipertensos() {
        FichaPacienteGateway fichaPacienteGateway = new FichaPacienteGateway(fichaPacienteDataSource);
        UsuarioGateway usuarioGateway = new UsuarioGateway(usuarioDataSource);
        NotificacaoGateway notificacaoGateway = new NotificacaoGateway(notificacaoDataSource);
        NotificarPacientesHipertensosUseCase useCase = new NotificarPacientesHipertensosUseCase(fichaPacienteGateway, usuarioGateway, notificacaoGateway);

        useCase.executar();
    }
}
