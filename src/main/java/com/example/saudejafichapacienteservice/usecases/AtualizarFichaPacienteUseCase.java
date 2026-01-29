package com.example.saudejafichapacienteservice.usecases;

import com.example.saudejafichapacienteservice.entidades.FichaPaciente;
import com.example.saudejafichapacienteservice.exceptions.BadArgumentException;
import com.example.saudejafichapacienteservice.gateways.FichaPacienteGateway;
import com.example.saudejafichapacienteservice.mappers.FichaPacienteMapper;
import dtos.requests.AtualizarFichaPacienteRequest;

public class AtualizarFichaPacienteUseCase {

    private final FichaPacienteGateway fichaPacienteGateway;

    public AtualizarFichaPacienteUseCase(FichaPacienteGateway fichaPacienteGateway) {
        this.fichaPacienteGateway = fichaPacienteGateway;
    }

    public FichaPaciente executar(AtualizarFichaPacienteRequest atualizarFichaPacienteRequest) {
        FichaPaciente fichaPaciente = fichaPacienteGateway.getFichaPacienteByPacienteId(atualizarFichaPacienteRequest.paciente());

        String mensagemErro = "";
        try {
            fichaPaciente.setGenero(atualizarFichaPacienteRequest.genero());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            fichaPaciente.setHipertensao(atualizarFichaPacienteRequest.hipertensao());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            fichaPaciente.setDiabetes(atualizarFichaPacienteRequest.diabetes());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            fichaPaciente.setAids(atualizarFichaPacienteRequest.aids());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }
        try {
            fichaPaciente.setObservacoes(atualizarFichaPacienteRequest.observacoes());
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        return fichaPacienteGateway.saveFichaPaciente(FichaPacienteMapper.toDto(fichaPaciente));
    }
}
