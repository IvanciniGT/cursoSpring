package com.curso.animalitos.repositorio;

import org.springframework.boot.autoconfigure.SpringBootApplication;

// Eso era antes!
// Busca entidades persistbles en este paquete (y subpaquetes)
//@EntityScan("com.curso.animalitos.repositorio")
// Busca entidades repos (u otros componentes que tenga en mi aplciacion) en este paquete (y subpaquetes)
//@ComponentScan("com.curso.animalitos.repositorio")
@SpringBootApplication // Busca, componentes, configuraciones, entidades.. de to-
                       // do lo que tenga en el paquete en el que está definida esta clase!
public class AplicacionDePruebaRepositorio {
}
