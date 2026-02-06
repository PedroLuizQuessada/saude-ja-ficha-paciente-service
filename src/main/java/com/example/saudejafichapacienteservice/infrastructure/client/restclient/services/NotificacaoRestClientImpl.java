package com.example.saudejafichapacienteservice.infrastructure.client.restclient.services;

import com.example.saudejafichapacienteservice.controllers.AutenticacaoController;
import com.example.saudejafichapacienteservice.datasources.AutenticacaoDataSource;
import com.example.saudejafichapacienteservice.datasources.NotificacaoDataSource;
import com.example.saudejafichapacienteservice.infrastructure.exceptions.ForbiddenException;
import com.example.saudejafichapacienteservice.infrastructure.exceptions.UnauthorizedException;
import dtos.requests.EnviarNotificacaoRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Service
@Profile("restclient")
public class NotificacaoRestClientImpl implements NotificacaoDataSource {

    @Value("${notificacao-service.base-url}")
    private String urlBase;

    @Value("${notificacao-service.audiencia}")
    private String audiencia;

    private final RestClient client;

    private final AutenticacaoController autenticacaoController;

    public NotificacaoRestClientImpl(RestClient client, AutenticacaoDataSource autenticacaoDataSource) {
        this.client = client;
        this.autenticacaoController = new AutenticacaoController(autenticacaoDataSource);
    }

    @Override
    public void enviarNotificacao(EnviarNotificacaoRequest enviarNotificacaoRequest) {
        client.post()
                .uri(urlBase + "/api/v1/notificacoes/enviar")
                .header("Authorization", "Bearer " + autenticacaoController.gerarTokenServico(audiencia))
                .body(enviarNotificacaoRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    var contentType = res.getHeaders().getContentType();
                    var charset = (contentType != null && contentType.getCharset() != null)
                            ? contentType.getCharset()
                            : StandardCharsets.UTF_8;

                    String body = StreamUtils.copyToString(res.getBody(), charset);

                    if (res.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                        throw new BadRequestException(
                                "Valores inválidos para a notificação a ser enviada. Corpo: " + body
                        );
                    }
                    if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        throw new UnauthorizedException();
                    }
                    if (res.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                        throw new ForbiddenException();
                    }
                    throw new RuntimeException(
                            "Falha no serviço de notificações (notificacao-service). Corpo: " + body
                    );
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    var contentType = res.getHeaders().getContentType();
                    var charset = (contentType != null && contentType.getCharset() != null)
                            ? contentType.getCharset()
                            : StandardCharsets.UTF_8;

                    String body = StreamUtils.copyToString(res.getBody(), charset);

                    throw new RuntimeException(
                            "Erro interno no serviço de notificações (notificacao-service). Corpo: " + body
                    );
                })
                .toEntity(Void.class);
    }

}
