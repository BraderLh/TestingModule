package com.codigo.semana7.infrastructure.repository;

import com.codigo.semana7.domain.model.Persona;
import com.codigo.semana7.infrastructure.entity.PersonaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonaJPARepositoryAdapterTest {

    @Mock
    PersonaJPARepository personaJPARepository;

    @InjectMocks
    PersonaJPARepositoryAdapter personaJPARepositoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personaJPARepositoryAdapter = new PersonaJPARepositoryAdapter(personaJPARepository);
    }

    @Test
    void createPersona() {
        //prepare object
        Persona persona =  new Persona(1L, "Paul", "Walker", new Date(), "Masculino");
        PersonaEntity personaEntity = new PersonaEntity(1L, "Paul", "Walker", new Date(), "Masculino");

        //Simulate and execute the crud operation
        Mockito.when(personaJPARepository.save(any(PersonaEntity.class))).thenReturn(personaEntity);
        Persona personaAdapter = personaJPARepositoryAdapter.createPersona(persona);

        //check and confirm the returned object (result) is not null
        assertNotNull(personaAdapter);
        assertEquals(persona.getId(), personaAdapter.getId());
    }

    @Test
    void findById_IsEmpty() {
        Mockito.when(personaJPARepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Persona> returnedPersona = personaJPARepositoryAdapter.getPersona(1L);
        assertTrue(returnedPersona.isEmpty());
    }

    @Test
    void findById_ExistingId_ReturnsPersona() {
        PersonaEntity personaEntity = new PersonaEntity(1L, "Dominic", "Toretto", new Date(), "M");
        Mockito.when(personaJPARepository.findById(1L)).thenReturn(Optional.of(personaEntity));
        Optional<Persona> returnedPersona = personaJPARepositoryAdapter.getPersona(1L);
        assertTrue(returnedPersona.isPresent());
        Persona persona = personaEntity.toDomainModel();
        //assertSame(persona, returnedPersona.get());
        assertEquals(persona.getId(), returnedPersona.get().getId());
        Assertions.assertNotNull(returnedPersona);
    }

    @Test
    void update_ExistingIdAndValidPersona_ReturnsUpdatedPersona() {
        Persona personaToUpdate =  new Persona(1L, "José Paolo", "Guerrero Gonzales", new Date(), "Masculino");
        PersonaEntity personaEntity = new PersonaEntity(1L, "José Paolo", "Guerrero Gonzales", new Date(), "Masculino");

        Mockito.when(personaJPARepository.findById(personaToUpdate.getId())).thenReturn(Optional.of(personaEntity));
        Mockito.when(personaJPARepository.save(any(PersonaEntity.class))).thenReturn(personaEntity);

        Optional<Persona> personaAdapter = personaJPARepositoryAdapter.updatePersona(personaToUpdate.getId(), personaToUpdate);

        assertNotNull(personaAdapter);
    }

    @Test
    void update_NonExistingId_ReturnsEmptyOptional() {
        Long personaId = 2L;
        PersonaEntity personaEntity = new PersonaEntity();

        Mockito.when(personaJPARepository.findById(personaId)).thenReturn(Optional.empty());
        Mockito.when(personaJPARepository.save(any(PersonaEntity.class))).thenReturn(personaEntity);

        Optional<Persona> personaAdapter = personaJPARepositoryAdapter.updatePersona(personaId, personaEntity.toDomainModel());

        assertNotNull(personaAdapter);
        assertFalse(personaAdapter.isPresent());
    }

    @Test
    void deleteById_NonExistingId_ReturnsFalse() {
        Mockito.doNothing().when(personaJPARepository).deleteById(null);
        boolean personaAdapter = personaJPARepositoryAdapter.deletePersona(null);
        assertFalse(personaAdapter);
    }

    @Test
    void deleteById_ExistingId_ReturnsTrue() {
        Long personaId = 1L;
        PersonaEntity personaEntity = new PersonaEntity(1L, "Dominic", "Toretto", new Date(), "M");
        boolean exists = true;
        Mockito.when(personaJPARepository.existsById(personaId)).thenReturn(exists);
        boolean personaAdapter = personaJPARepositoryAdapter.deletePersona(personaEntity.getId());
        System.out.println("exists = " + exists);
        assertEquals(exists, personaAdapter);
    }
}