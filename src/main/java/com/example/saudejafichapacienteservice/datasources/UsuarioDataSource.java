package com.example.saudejafichapacienteservice.datasources;

import dtos.requests.PacienteIdPageRequest;
import dtos.responses.UsuarioEmailPageResponse;

public interface UsuarioDataSource {
    UsuarioEmailPageResponse getUsuarioPacienteEmailFromId(PacienteIdPageRequest pacienteIdPageRequest);
}
