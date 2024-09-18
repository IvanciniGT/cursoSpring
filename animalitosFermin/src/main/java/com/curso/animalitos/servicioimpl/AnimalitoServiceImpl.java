package com.curso.animalitos.servicioimpl;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.repositorio.repository.AnimalitoRepositorio;
import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.ModificarAnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

// Qué hace esta anotación? Es una variable de Component que al que lo vea sabe que conceptualmente esta implementación es un Servicio
// Pero... por ser una variante de @Component:
// - Spring creará una instancia de esta clase cuando arranque... y la guarda en caché
// - Cuando alguien pida un AnimalitoService le entregará esa instancia que tiene en caché
// - Como de esta clase, Spring es quién va a crear la instancia, en el constructor puedo pedir dependencias
@Service
public class AnimalitoServiceImpl implements AnimalitoService {

    private final AnimalitoRepositorio repositorio;

    public AnimalitoServiceImpl(AnimalitoRepositorio repositorio /*Y más cosas que necesite*/){
        this.repositorio=repositorio;
    }

    @Override
    public AnimalitoDTO nuevoAnimal(NuevoAnimalitoDTO nuevoAnimal) {
        AnimalitoEntity entidadAPersistir = AnimalitoEntity.builder()
                .nombre(nuevoAnimal.getNombre())
                .tipo(nuevoAnimal.getTipo())
                .fechaNacimiento(nuevoAnimal.getFechaNacimiento())
                .build();

        AnimalitoEntity animalitoPersistido = repositorio.save(entidadAPersistir);

        AnimalitoDTO datosDelAnimalitoADevolver = new AnimalitoDTO();
        datosDelAnimalitoADevolver.setNombre(nuevoAnimal.getNombre());
        datosDelAnimalitoADevolver.setTipo(nuevoAnimal.getTipo());
        datosDelAnimalitoADevolver.setFechaNacimiento((nuevoAnimal.getFechaNacimiento()));
        datosDelAnimalitoADevolver.setId(animalitoPersistido.getId());

        return datosDelAnimalitoADevolver;
    }

    @Override
    public AnimalitoDTO modificarAnimal(Long id, ModificarAnimalitoDTO modificacionAnimal) {
        return null;
    }

    @Override
    public void borrarAnimal(Long id) {

    }

    @Override
    public List<AnimalitoDTO> buscarTodos() {
        return null;
    }

    @Override
    public AnimalitoDTO recuperarPorId(Long id) {
        return null;
    }
}
