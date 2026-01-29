package com.example.saudejafichapacienteservice.entidades;

import com.example.saudejafichapacienteservice.exceptions.BadArgumentException;
import enums.GeneroEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class FichaPaciente {
    private final Long id;
    private final Long paciente;
    private final LocalDate dataNascimento;
    private GeneroEnum genero;
    @Setter
    private Boolean hipertensao;
    @Setter
    private Boolean diabetes;
    @Setter
    private Boolean aids;
    @Setter
    private String observacoes;

    public FichaPaciente(Long id, Long paciente, LocalDate dataNascimento, GeneroEnum genero, Boolean hipertensao, Boolean diabetes, Boolean aids, String observacoes) {
        String mensagemErro = "";

        try {
            validarPaciente(paciente);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarDataNascimento(dataNascimento);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        try {
            validarGenero(genero);
        }
        catch (RuntimeException e) {
            mensagemErro = mensagemErro + " " + e.getMessage();
        }

        if (!mensagemErro.isEmpty())
            throw new BadArgumentException(mensagemErro);

        this.id = id;
        this.paciente = paciente;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.hipertensao = hipertensao;
        this.diabetes = diabetes;
        this.aids = aids;
        this.observacoes = observacoes;
    }

    public void setGenero(GeneroEnum genero) {
        validarGenero(genero);
        this.genero = genero;
    }

    private void validarPaciente(Long paciente) {
        if (Objects.isNull(paciente)) {
            throw new BadArgumentException("A ficha de paciente deve possuir um paciente.");
        }
    }

    private void validarDataNascimento(LocalDate dataNascimento) {
        if (Objects.isNull(dataNascimento)) {
            throw new BadArgumentException("A ficha de paciente deve possuir a data de nascimento do paciente.");
        }
    }

    private void validarGenero(GeneroEnum genero) {
        if (Objects.isNull(genero)) {
            throw new BadArgumentException("A ficha de paciente deve possuir o gênero do paciente.");
        }
    }
}
