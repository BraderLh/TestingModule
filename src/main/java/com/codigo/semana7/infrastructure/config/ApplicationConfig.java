package com.codigo.semana7.infrastructure.config;

import com.codigo.semana7.application.service.PersonaService;
import com.codigo.semana7.application.usecase.PersonaServiceImpl;
import com.codigo.semana7.domain.ports.out.PersonaOut;
import com.codigo.semana7.infrastructure.repository.PersonaJPARepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PersonaService personaService(PersonaOut personaOut){
        return new PersonaService(new PersonaServiceImpl(personaOut));
    }

    @Bean
    public PersonaOut personaOut(PersonaJPARepositoryAdapter personaJPARepositoryAdapter){
        return personaJPARepositoryAdapter;
    }
}
