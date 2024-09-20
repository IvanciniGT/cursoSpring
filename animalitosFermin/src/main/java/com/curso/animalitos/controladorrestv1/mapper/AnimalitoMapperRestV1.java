package com.curso.animalitos.controladorrestv1.mapper;

import com.curso.animalitos.controladorrestv1.dtos.AnimalitoRestDTOv1;
import com.curso.animalitos.controladorrestv1.dtos.NuevoAnimalitoRestDTOv1;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Esta anotación hace 2 cosas:
// Por un lado, le pide a MapStruct que cree una clase implementando esta interfaz... que haga el mapeo. Lo que antes habíamos escrito a mano
// MapStruct tiene integración directa con Spring... y con esa parte de componentModel = "spring"
// Lo que hacemos es que Spring genere una instancia de esa clase y la entregue cuando alguien pida un AnimalitoMapper
@Mapper(componentModel = "spring")
public interface AnimalitoMapperRestV1 {

    // Mapear de NuevoAnimalitoRestDTOv1 (con fechaNacimiento como String) a NuevoAnimalitoDTO (con fechaNacimiento como LocalDateTime)
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento", qualifiedByName = "stringToLocalDateTime")
    NuevoAnimalitoDTO nuevoAnimalitoRestDTOv1_2_NuevoAnimalitoDTO(NuevoAnimalitoRestDTOv1 nuevoAnimal);

    // Mapear de AnimalitoDTO (con fechaNacimiento como LocalDateTime) a AnimalitoRestDTOv1 (con fechaNacimiento como String)
    @Mapping(source = "fechaNacimiento", target = "fechaNacimiento", qualifiedByName = "localDateTimeToString")
    AnimalitoRestDTOv1 animalitoDTO2AnimalitoRestDTOv1(AnimalitoDTO devuelto);

    // Conversión personalizada de String a LocalDateTime
    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return fechaNacimiento != null ? LocalDateTime.parse(fechaNacimiento, formatter) : null;
    }

    // Conversión personalizada de LocalDateTime a String
    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return fechaNacimiento != null ? fechaNacimiento.format(formatter) : null;
    }
}
