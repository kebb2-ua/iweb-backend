package es.ua.iweb.paqueteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BackendPaqueteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendPaqueteriaApplication.class, args);
    }

}
