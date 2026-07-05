package ar.edu.utn.frba.ddsi.logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class LogisticaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogisticaServiceApplication.class, args);
    }
}

