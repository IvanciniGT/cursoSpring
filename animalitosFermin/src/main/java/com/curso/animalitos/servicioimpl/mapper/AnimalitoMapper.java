package com.curso.animalitos.servicioimpl.mapper;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;

public interface AnimalitoMapper {

    AnimalitoDTO animalEntity2AnimalDTO(AnimalitoEntity entidad);
    AnimalitoEntity nuevoAnimalDTO2AnimalEntity(NuevoAnimalitoDTO datos) ;

}
