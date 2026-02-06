package com.example.saudejafichapacienteservice.infrastructure.rotinas.springscheduling.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("springscheduling")
public class SpringSchedulingConfig {
}
