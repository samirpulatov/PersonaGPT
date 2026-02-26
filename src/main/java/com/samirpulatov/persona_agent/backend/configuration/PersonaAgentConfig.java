package com.samirpulatov.persona_agent.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
// Binds configuration properties with prefix "openai" from application.yaml/properties
@ConfigurationProperties(prefix = "openai")
public class PersonaAgentConfig {

    // OpenAI API key loaded from the external configuration
    private String apiKey;

    // Returns the configured OpenAI API key
    public String getApiKey() {
        return apiKey;
    }

    // Sets the OpenAI API key (used by Spring during property binding)
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
