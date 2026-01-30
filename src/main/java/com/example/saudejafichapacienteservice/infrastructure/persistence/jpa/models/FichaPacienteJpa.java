package com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.models;

import com.example.saudejafichapacienteservice.infrastructure.exceptions.BadJpaArgumentException;
import enums.GeneroEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "fichas_paciente")
@Getter
@NoArgsConstructor
@Profile("jpa")
public class FichaPacienteJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long paciente;

    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GeneroEnum genero;

    @Column
    private Boolean hipertensao;

    @Column
    private Boolean diabetes;

    @Column
    private Boolean aids;

    @Column
    private String observacoes;

    public FichaPacienteJpa(Long id, Long paciente, LocalDate dataNascimento, GeneroEnum genero, Boolean hipertensao, Boolean diabetes, Boolean aids, String observacoes) {
        String message = "";

        try {
            validarPaciente(paciente);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }
        try {
            validarDataNascimento(dataNascimento);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }
        try {
            validarGenero(genero);
        }
        catch (RuntimeException e) {
            message = message + " " + e.getMessage();
        }

        if (!message.isEmpty())
            throw new BadJpaArgumentException(message);

        this.id = id;
        this.paciente = paciente;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.hipertensao = hipertensao;
        this.diabetes = diabetes;
        this.aids = aids;
        this.observacoes = observacoes;
    }

    private void validarPaciente(Long paciente) {
        if (Objects.isNull(paciente))
            throw new BadJpaArgumentException("A ficha de paciente deve possuir um paciente para ser armazenada no banco de dados.");
    }

    private void validarDataNascimento(LocalDate dataNascimento) {
        if (Objects.isNull(dataNascimento))
            throw new BadJpaArgumentException("A ficha de paciente deve possuir a data de nascimento do paciente para ser armazenada no banco de dados.");
    }

    private void validarGenero(GeneroEnum genero) {
        if (Objects.isNull(genero))
            throw new BadJpaArgumentException("A ficha de paciente deve possuir o gênero do paciente para ser armazenada no banco de dados.");
    }
}
