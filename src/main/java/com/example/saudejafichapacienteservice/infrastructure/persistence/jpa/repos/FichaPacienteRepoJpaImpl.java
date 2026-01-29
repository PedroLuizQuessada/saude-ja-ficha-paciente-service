package com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.repos;

import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.mappers.FichaPacienteJpaDtoMapper;
import com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.models.FichaPacienteJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Profile("jpa")
public class FichaPacienteRepoJpaImpl implements FichaPacienteDataSource {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FichaPacienteJpaDtoMapper fichaPacienteJpaDtoMapper;
    @Override
    public Long countByPaciente(Long pacienteId) {
        Query query = entityManager.createQuery("SELECT count(*) FROM FichaPacienteJpa fichaPaciente WHERE fichaPaciente.paciente = :pacienteId");
        query.setParameter("pacienteId", pacienteId);
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public FichaPacienteDto saveFichaPaciente(FichaPacienteDto fichaPacienteDto) {
        FichaPacienteJpa fichaPacienteJpa = fichaPacienteJpaDtoMapper.toJpa(fichaPacienteDto);
        fichaPacienteJpa = entityManager.merge(fichaPacienteJpa);
        return fichaPacienteJpaDtoMapper.toDto(fichaPacienteJpa);
    }

    @Override
    public Optional<FichaPacienteDto> getFichaPacienteByPacienteId(Long pacienteId) {
        Query query = entityManager.createQuery("SELECT fichaPaciente FROM FichaPacienteJpa fichaPaciente WHERE fichaPaciente.paciente = :pacienteId");
        query.setParameter("pacienteId", pacienteId);
        try {
            FichaPacienteJpa fichaPacienteJpa = (FichaPacienteJpa) query.getSingleResult();
            return Optional.of(fichaPacienteJpaDtoMapper.toDto(fichaPacienteJpa));
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteFichaPacienteById(Long id) {

    }
}
