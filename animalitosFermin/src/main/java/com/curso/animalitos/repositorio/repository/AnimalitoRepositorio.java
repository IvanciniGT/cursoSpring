package com.curso.animalitos.repositorio.repository;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalitoRepositorio extends JpaRepository<AnimalitoEntity, Long> {
    // Ya tengo todos los métodos CRUD

    // Y Spring empieza a hacer MAGIA:
    // Cuando arranque Spring, identificará esto como un repositorio... por extender JpaRepository.. y Spring crea la clase:
    // AnimalitoRepositorioImpl, y rellena todos los métodos CRUD... en automático.

    // Quiero una función custom para buscar animalitos por TIPO
    List<AnimalitoEntity> findByTipo(String tipo);
    List<AnimalitoEntity> findByNombre(String nombre);
    // Y SPRING sigue haciendo Magia... En la clase que genere, me implementa esta función en automático...
    // por haberla llamado: findBy   con el sufijo: Tipo

    List<AnimalitoEntity> findByTipoStartingWith(String tipo); // En auto me monta un LIKE "%tipo"
    List<AnimalitoEntity> findByTipoEndingWith(String tipo);// En auto me monta un LIKE "tipo%"
    List<AnimalitoEntity> findByTipoContaining(String tipo);// En auto me monta un LIKE "%tipo%"
    // Y otras 100 variantes que se implementan en automático

}


