package com.curso.animalitos.servicio.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
//@EqualsAndHashCode(callSuper=true)
public class NuevoAnimalitoDTO{ //} extends ModificarAnimalitoDTO{
    private String nombre;

    private String tipo;

    private LocalDateTime fechaNacimiento;

}
