package br.com.fiap.msclients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication
@AutoConfigureAfter(FlywayAutoConfiguration.class)
public class MsClientsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsClientsApplication.class, args);
    }
}

