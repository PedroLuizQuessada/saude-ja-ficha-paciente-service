package com.example.saudejafichapacienteservice.infrastructure.client.restclient.services;

import com.example.saudejafichapacienteservice.controllers.AutenticacaoController;
import com.example.saudejafichapacienteservice.datasources.AutenticacaoDataSource;
import com.example.saudejafichapacienteservice.datasources.UsuarioDataSource;
import com.example.saudejafichapacienteservice.infrastructure.exceptions.ForbiddenException;
import com.example.saudejafichapacienteservice.infrastructure.exceptions.UnauthorizedException;
import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Profile("restclient")
public class UsuarioRestClientImpl implements UsuarioDataSource {

    @Value("${usuario-service.base-url}")
    private String urlBase;

    @Value("${usuario-service.audiencia}")
    private String audiencia;

    private final RestClient client;

    private final AutenticacaoController autenticacaoController;

    private final ClientResponseService clientResponseService;

    public UsuarioRestClientImpl(RestClient client, AutenticacaoDataSource autenticacaoDataSource, ClientResponseService clientResponseService) {
        this.client = client;
        this.autenticacaoController = new AutenticacaoController(autenticacaoDataSource);
        this.clientResponseService = clientResponseService;
    }

    @Override
    public UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest) {
        return client.post()
                .uri(urlBase + "/api/v1/usuarios/emails")
                .header("Authorization", "Bearer " + autenticacaoController.gerarTokenServico(audiencia))
                .body(pacienteIdPageRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        throw new UnauthorizedException();
                    }
                    if (res.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                        throw new ForbiddenException();
                    }
                    else {
                        String body = clientResponseService.getResponseBody(res);
                        throw new RuntimeException(
                                "Falha no serviço de usuários (usuario-service). Corpo: " + body
                        );
                    }
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    String body = clientResponseService.getResponseBody(res);

                    throw new RuntimeException(
                            "Erro interno no serviço de usuários (usuario-service). Corpo: " + body
                    );
                })
                .toEntity(UsuarioEmailPageResponse.class).getBody();
    }
}
