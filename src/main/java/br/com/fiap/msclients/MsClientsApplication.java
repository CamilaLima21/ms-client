package br.com.fiap.msclients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@AutoConfigureAfter(FlywayAutoConfiguration.class)
@EnableFeignClients
public class MsClientsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsClientsApplication.class, args);
    }
}

