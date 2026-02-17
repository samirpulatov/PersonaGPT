package com.samirpulatov.persona_agent;

import com.samirpulatov.persona_agent.configuration.PersonaAgentConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PersonaAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonaAgentApplication.class, args);


	}

}
