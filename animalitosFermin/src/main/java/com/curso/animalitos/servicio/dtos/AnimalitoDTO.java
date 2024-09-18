package com.curso.animalitos.servicio.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AnimalitoDTO extends NuevoAnimalitoDTO{

    private Long id;

}
