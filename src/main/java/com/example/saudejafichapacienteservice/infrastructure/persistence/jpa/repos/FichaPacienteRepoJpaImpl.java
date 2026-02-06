package com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.repos;

import com.example.saudejafichapacienteservice.datasources.FichaPacienteDataSource;
import com.example.saudejafichapacienteservice.dtos.FichaPacienteDto;
import com.example.saudejafichapacienteservice.dtos.PacienteIdDtoPage;
import com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.mappers.FichaPacienteJpaDtoMapper;
import com.example.saudejafichapacienteservice.infrastructure.persistence.jpa.models.FichaPacienteJpa;
import jakarta.persistence.*;
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
    @Transactional
    public void deleteFichaPacienteById(Long pacienteId) {
        Query query = entityManager.createQuery("DELETE FROM FichaPacienteJpa fichaPaciente WHERE fichaPaciente.paciente = :pacienteId");
        query.setParameter("pacienteId", pacienteId);
        query.executeUpdate();
    }

    @Override
    public PacienteIdDtoPage getPacientesHipertensosId(int page, int size) {
        int offset = Math.max(0, page) * Math.max(1, size);

        String jpql = "SELECT fichaPaciente.paciente FROM FichaPacienteJpa fichaPaciente WHERE fichaPaciente.hipertensao = true ORDER BY fichaPaciente.id";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class)
                .setFirstResult(offset)
                .setMaxResults(size);

        return new PacienteIdDtoPage(page, size, query.getResultList());
    }
}
