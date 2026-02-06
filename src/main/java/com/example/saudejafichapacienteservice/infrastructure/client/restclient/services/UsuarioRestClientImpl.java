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
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Service
@Profile("restclient")
public class UsuarioRestClientImpl implements UsuarioDataSource {

    @Value("${usuario-service.base-url}")
    private String urlBase;

    @Value("${usuario-service.audiencia}")
    private String audiencia;

    private final RestClient client;

    private final AutenticacaoController autenticacaoController;

    public UsuarioRestClientImpl(RestClient client, AutenticacaoDataSource autenticacaoDataSource) {
        this.client = client;
        this.autenticacaoController = new AutenticacaoController(autenticacaoDataSource);
    }

    @Override
    public UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest) {
        return client.post()
                .uri(urlBase + "/api/v1/usuarios/emails")
                .header("Authorization", "Bearer " + autenticacaoController.gerarTokenServico(audiencia))
                .body(pacienteIdPageRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    var contentType = res.getHeaders().getContentType();
                    var charset = (contentType != null && contentType.getCharset() != null)
                            ? contentType.getCharset()
                            : StandardCharsets.UTF_8;

                    String body = StreamUtils.copyToString(res.getBody(), charset);

                    if (res.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        throw new UnauthorizedException();
                    }
                    if (res.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                        throw new ForbiddenException();
                    }
                    throw new RuntimeException(
                            "Falha no serviço de usuários (usuario-service). Corpo: " + body
                    );
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    var contentType = res.getHeaders().getContentType();
                    var charset = (contentType != null && contentType.getCharset() != null)
                            ? contentType.getCharset()
                            : StandardCharsets.UTF_8;

                    String body = StreamUtils.copyToString(res.getBody(), charset);

                    throw new RuntimeException(
                            "Erro interno no serviço de usuários (usuario-service). Corpo: " + body
                    );
                })
                .toEntity(UsuarioEmailPageResponse.class).getBody();
    }
}
