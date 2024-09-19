package com.curso.animalitos.controladorrestv1.mapper;

import com.curso.animalitos.controladorrestv1.dtos.AnimalitoRestDTOv1;
import com.curso.animalitos.controladorrestv1.dtos.NuevoAnimalitoRestDTOv1;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import org.mapstruct.Mapper;

// Esta anotación hace 2 cosas:
// Por un lado, le pide a MapStruct que cree una clase implementando esta interfaz... que haga el mapeo. Lo que antes habíamos escrito a mano
// MapStruct tiene integración directa con Spring... y con esa parte de componentModel = "spring"
// Lo que hacemos es que Spring genere una instancia de esa clase y la entregue cuando alguien pida un AnimalitoMapper
@Mapper(componentModel = "spring")
public interface AnimalitoMapperRestV1 {

    NuevoAnimalitoDTO nuevoAnimalitoRestDTOv1_2_NuevoAnimalitoDTO(NuevoAnimalitoRestDTOv1 nuevoAnimal);

    AnimalitoRestDTOv1 animalitoDTO2AnimalitoRestDTOv1(AnimalitoDTO devuelto);
}
