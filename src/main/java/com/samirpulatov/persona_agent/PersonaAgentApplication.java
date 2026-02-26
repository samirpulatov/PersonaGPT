package com.samirpulatov.persona_agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PersonaAgentApplication implements CommandLineRunner {


    private final static Logger LOGGER = LoggerFactory.getLogger(PersonaAgentApplication.class);

	public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // set headless to false so that browser can be opened
		SpringApplication.run(PersonaAgentApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        openBrowser("http://localhost:8080/");
    }

    private void openBrowser(String url) {
        LOGGER.info("Desktop supported: " + Desktop.isDesktopSupported());
        LOGGER.info("Graphics environment headless: " + java.awt.GraphicsEnvironment.isHeadless());
        if(Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    // timeout to let server start
                    Thread.sleep(1000);
                    Desktop.getDesktop().browse(URI.create(url));
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Error while trying to open default browser ");
                }
            }).start();

        }

    }

}
