package com.curso.animalitos.servicioimpl.mapper;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import org.mapstruct.Mapper;

// Esta anotación hace 2 cosas:
// Por un lado, le pide a MapStruct que cree una clase implementando esta interfaz... que haga el mapeo. Lo que antes habíamos escrito a mano
// MapStruct tiene integración directa con Spring... y con esa parte de componentModel = "spring"
// Lo que hacemos es que Spring genere una instancia de esa clase y la entregue cuando alguien pida un AnimalitoMapper
@Mapper(componentModel = "spring")
public interface AnimalitoMapper {

    AnimalitoDTO animalEntity2AnimalDTO(AnimalitoEntity entidad);
    AnimalitoEntity nuevoAnimalDTO2AnimalEntity(NuevoAnimalitoDTO datos) ;

}
