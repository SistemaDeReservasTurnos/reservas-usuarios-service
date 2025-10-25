package com.servicio.reservas.usuarios;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservasUsuariosServiceApplication {


    public static void main(String[] args) {
        Dotenv.configure().systemProperties().load();
        SpringApplication.run(ReservasUsuariosServiceApplication.class, args);
    }

}
