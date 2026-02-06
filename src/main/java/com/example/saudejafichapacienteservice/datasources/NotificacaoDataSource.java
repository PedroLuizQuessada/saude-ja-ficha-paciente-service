package com.example.saudejafichapacienteservice.datasources;

import dtos.requests.EnviarNotificacaoRequest;

public interface NotificacaoDataSource {
    void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest);
}
