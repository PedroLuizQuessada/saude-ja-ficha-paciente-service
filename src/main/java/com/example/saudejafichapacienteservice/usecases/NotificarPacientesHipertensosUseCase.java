package com.example.saudejafichapacienteservice.usecases;

import com.example.saudejafichapacienteservice.entidades.PacienteIdPage;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;
import com.example.saudejafichapacienteservice.gateways.NotificacaoGateway;
import com.example.saudejafichapacienteservice.gateways.UsuarioGateway;
import dtos.requests.EnviarNotificacaoRequest;
import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

import java.util.Objects;

public class NotificarPacientesHipertensosUseCase {
    private static final String ASSUNTO_NOTIFICACAO = "Hora de realizar seu retorno para checar sua hipertensão.";
    private static final String MENSAGEM_NOTIFICACAO = "É muito importante realizar um acompanhamento regular na unidade de saúde mais próxima para garantir o controle sobre a hipertensão.";
    private final FichaPacienteGateway fichaPacienteGateway;
    private final UsuarioGateway usuarioGateway;
    private final NotificacaoGateway notificacaoGateway;

    public NotificarPacientesHipertensosUseCase(FichaPacienteGateway fichaPacienteGateway, UsuarioGateway usuarioGateway, NotificacaoGateway notificacaoGateway) {
        this.fichaPacienteGateway = fichaPacienteGateway;
        this.usuarioGateway = usuarioGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    public void executar() {
        int page = 0;
        int size = 200;
        int resultados = size;
        while (Objects.equals(resultados, size)) {
            PacienteIdPage pacienteIdPage = fichaPacienteGateway.getPacientesHipertensosId(page, size);
            PacienteIdPageRequest pacienteIdPageRequest = new PacienteIdPageRequest(pacienteIdPage.getPage(), pacienteIdPage.getSize(), pacienteIdPage.getContent());

            UsuarioEmailPageResponse usuarioEmailPageResponse = usuarioGateway.getUsuarioPacienteEmailFromId(pacienteIdPageRequest);

            for (String email : usuarioEmailPageResponse.getContent()) {
                notificacaoGateway.enviarNotificacao(new EnviarNotificacaoRequest(email, ASSUNTO_NOTIFICACAO, MENSAGEM_NOTIFICACAO));
            }

            resultados = pacienteIdPage.getContent().size();
        }
    }
}
