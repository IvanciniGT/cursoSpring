package com.curso.animalitos.servicio;

import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.ModificarAnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;

import java.util.List;
import java.util.Optional;

public interface AnimalitoService {
    AnimalitoDTO nuevoAnimal(NuevoAnimalitoDTO nuevoAnimal);
    AnimalitoDTO modificarAnimal(Long id, ModificarAnimalitoDTO modificacionAnimal);
    void borrarAnimal(Long id);
    List<AnimalitoDTO> buscarTodos();
    Optional<AnimalitoDTO> recuperarPorId(Long id);
}
