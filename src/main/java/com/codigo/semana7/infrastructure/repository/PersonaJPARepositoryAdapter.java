package com.codigo.semana7.infrastructure.repository;

import com.codigo.semana7.domain.model.Persona;
import com.codigo.semana7.domain.ports.out.PersonaOut;
import com.codigo.semana7.infrastructure.entity.PersonaEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonaJPARepositoryAdapter implements PersonaOut {

    private final PersonaJPARepository personaJPARepository;

    public PersonaJPARepositoryAdapter(PersonaJPARepository personaJPARepository) {
        this.personaJPARepository = personaJPARepository;
    }

    @Override
    public Persona createPersona(Persona persona) {
        PersonaEntity personaEntity = PersonaEntity.fromDomainModel(persona);
        return personaJPARepository.save(personaEntity).toDomainModel();
    }

    @Override
    public Optional<Persona> getPersona(Long id) {
        return personaJPARepository.findById(id).map(PersonaEntity::toDomainModel);
    }

    @Override
    public Optional<Persona> updatePersona(Long id, Persona persona) {
        if(personaJPARepository.existsById(id)){
            PersonaEntity personaEntity = PersonaEntity.fromDomainModel(persona);
            return Optional.of(personaJPARepository.save(personaEntity).toDomainModel());
        }
        return Optional.empty();
    }

    @Override
    public boolean deletePersona(Long id) {
        if(personaJPARepository.existsById(id)){
            personaJPARepository.deleteById(id);
            return true;
        }
        return false;
    }
}
