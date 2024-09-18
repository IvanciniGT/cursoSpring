package com.curso.animalitos.repositorio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data // Getters, y setters de todas las propiedades, un .equals personalizado y un .toString()
@SuperBuilder
@NoArgsConstructor
// Vamos a usar JPA: Es un estándar de JEE que define persistencia automatizada de objetos JAVA en BBDD
// En JPA tenemos algunas anotaciones adicionales
@Entity // Entidad persistible en BBDD
@Table( name = "Animalitos" )
public class AnimalitoEntity {

    //public AnimalitoEntity(){} @NoArgsConstructor ES UN REQUISITO DE JPA para las @Entity

    @Id // Id de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false, length=50)
    private String nombre;

    @Column(nullable=false, length=50, updatable = false)
    private String tipo;

    @Column(nullable=false, updatable = false)
    private LocalDateTime fechaNacimiento;

}
// Acabo de definir en este fichero, la estructura de la BBDD para la tabla Animalitos.
// En automático, al arrancar mi app, esa tabla se creará si no existe en la BBDD:
// CREATE TABLE Animalitos(
// id NUMBER AUTOINCREMENT PRIMARY KEY;
// ... PASO... lo hace JAVA POR MI (JPA) em concreto usamos una librería dentro de Spring llamada HIBERNATE que es la que implementa el estandar JPA y me regala esto.
// );