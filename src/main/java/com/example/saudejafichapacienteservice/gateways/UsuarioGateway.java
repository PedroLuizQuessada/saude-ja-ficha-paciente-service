package com.example.saudejafichapacienteservice.gateways;

import com.example.saudejafichapacienteservice.datasources.UsuarioDataSource;
import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

public class UsuarioGateway {
    private final UsuarioDataSource usuarioDataSource;

    public UsuarioGateway(UsuarioDataSource usuarioDataSource) {
        this.usuarioDataSource = usuarioDataSource;
    }

    public UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest) {
        return usuarioDataSource.getUsuarioPacienteEmailFromId(pacienteIdPageRequest);
    }
}
