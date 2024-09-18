package com.curso.animalitos.servicioimpl;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.repositorio.repository.AnimalitoRepositorio;
import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.ModificarAnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import com.curso.animalitos.servicioimpl.mapper.AnimalitoMapper;
import com.curso.emails.service.EmailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Qué hace esta anotación? Es una variable de Component que al que lo vea sabe que conceptualmente esta implementación es un Servicio
// Pero... por ser una variante de @Component:
// - Spring creará una instancia de esta clase cuando arranque... y la guarda en caché
// - Cuando alguien pida un AnimalitoService le entregará esa instancia que tiene en caché
// - Como de esta clase, Spring es quién va a crear la instancia, en el constructor puedo pedir dependencias
@Service
@RequiredArgsConstructor // Me genera en auto el constructor para las variables final
public class AnimalitoServiceImpl implements AnimalitoService {

    private final AnimalitoRepositorio repositorio;
    private final EmailsService servicioEmails;
    private final AnimalitoMapper mapeador;

    // Spring, cuando arranca, COMO PARTE DE SU FLUJO QUE DEBO CONOCER,
    // lo primero que hace es buscar en archivo llamado: application.properties
    // Con la anotación @Value, puedo recuperar valores de ese fichero
    @Value("${animalitos.email.destinatario}")
    private String DESTINATARIO_EMAILS;
    /*
    public AnimalitoServiceImpl(AnimalitoRepositorio repositorio, EmailsService servicioEmails ){
        this.repositorio=repositorio;
        this.servicioEmails = servicioEmails;
    }
    */

    @Override
    public AnimalitoDTO nuevoAnimal(NuevoAnimalitoDTO nuevoAnimal) {

        AnimalitoEntity animalitoPersistido = repositorio.save(mapeador.nuevoAnimalDTO2AnimalEntity(nuevoAnimal));

        servicioEmails.enviarEmail(DESTINATARIO_EMAILS,
                "Nuevo animalito disponible",
                "Tenemos a la venta a "+nuevoAnimal.getNombre()+" que es un "+nuevoAnimal.getTipo()+" muy bonito. Ven a verlo!");

        return mapeador.animalEntity2AnimalDTO(animalitoPersistido);
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
        List<AnimalitoEntity> animalitosDelRepositorio = repositorio.findAll();
        /*
        List<AnimalitoDTO> nuevo = new ArrayList<>();
        for(AnimalitoEntity animalito: animalitosDelRepositorio){
            nuevo.add(mapeador.animalEntity2AnimalDTO(animalito));
        }
        return nuevo;*/

        return animalitosDelRepositorio.stream()
                .map( mapeador::animalEntity2AnimalDTO )
                .collect(Collectors.toList());
    }

    @Override
    public AnimalitoDTO recuperarPorId(Long id) {
        return null;
    }
}
