package com.example.saudejafichapacienteservice.gateways;

import com.example.saudejafichapacienteservice.datasources.NotificacaoDataSource;
import dtos.requests.EnviarNotificacaoRequest;

public class NotificacaoGateway {
    private final NotificacaoDataSource notificacaoDataSource;

    public NotificacaoGateway(NotificacaoDataSource notificacaoDataSource) {
        this.notificacaoDataSource = notificacaoDataSource;
    }

    public void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest) {
        notificacaoDataSource.enviarNotificacao(enviarNotificacaoRequest);
    }
}
