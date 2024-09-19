package com.curso.animalitos.controladorrestv1.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnimalitoRestDTOv1 extends NuevoAnimalitoRestDTOv1 {

    private Long id;

}
