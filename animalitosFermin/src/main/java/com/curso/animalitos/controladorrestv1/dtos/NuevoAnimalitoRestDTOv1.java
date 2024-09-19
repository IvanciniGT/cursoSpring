package com.curso.animalitos.controladorrestv1.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
public class NuevoAnimalitoRestDTOv1 extends ModificarAnimalitoRestDTOv1 {

    private String tipo;

    private LocalDateTime fechaNacimiento;

}
