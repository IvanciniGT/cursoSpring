package com.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiAplicacion {

    public static void main(String[] args ){ // Inversión de control
        SpringApplication.run(MiAplicacion.class, args);
    }

}
