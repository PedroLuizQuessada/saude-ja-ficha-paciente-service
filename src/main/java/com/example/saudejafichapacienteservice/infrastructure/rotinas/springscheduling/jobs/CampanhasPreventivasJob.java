package com.example.saudejafichapacienteservice.infrastructure.rotinas.springscheduling.jobs;

import com.example.saudejafichapacienteservice.controllers.NotificacaoController;
import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.datasources.NotificacaoDataSource;
import com.example.saudejafichapacienteservice.datasources.UsuarioDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("springscheduling")
public class CampanhasPreventivasJob {

    private final NotificacaoController notificacaoController;

    public CampanhasPreventivasJob(FichaPacienteDataSource fichaPacienteDataSource, UsuarioDataSource usuarioDataSource, NotificacaoDataSource notificacaoDataSource) {
        this.notificacaoController = new NotificacaoController(fichaPacienteDataSource, usuarioDataSource, notificacaoDataSource);
    }

    @Scheduled(cron = "0 0 9 1 * ?", zone = "America/Sao_Paulo")
    public void notificarPacientesHipertensos() {
        log.info("Enviando notificação para pacientes hipertensos");
        notificacaoController.notificarPacientesHipertensos();
        log.info("Notificação enviada para pacientes hipertensos");
    }
}
