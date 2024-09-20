package com.curso.animalitos.controladorrestv1;

import com.curso.animalitos.controladorrestv1.dtos.AnimalitoRestDTOv1;
import com.curso.animalitos.controladorrestv1.dtos.NuevoAnimalitoRestDTOv1;
import com.curso.animalitos.controladorrestv1.mapper.AnimalitoMapperRestV1;
import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
// Es un subtipo de @Component, pero no aporta solo SEMANTICA, APORTA FUNCIONALIDAD en SPRING
// Spring va a hacer cosas por ser esto un controlador rest... y debo configurlo
@RequiredArgsConstructor
///api/v1/animalitos/123
@RequestMapping("/api/v1") // Expón esto mediante http, en la ruta del servidor: /api/v1
// https://servidor/api/v1
public class AnimalitoRestControllerV1 {

    private final AnimalitoMapperRestV1 mapper;
    private final AnimalitoService servicio;

    //@PreAuthorize("hasRole('ADMIN')") // dentro tenemos que dar la condición de preautorización. Ahi dentro Spring tiene un lenguaje propio llamado SPEL
    //@PreAuthorize("isAuthenticated()") // dentro tenemos que dar la condición de preautorización. Ahi dentro Spring tiene un lenguaje propio llamado SPEL
    //@Secured("ADMIN")
    @RolesAllowed("ADMIN")
    @PostMapping("/animalitos") // Se concatena la ruta con la de arriba
    // https://servidor/api/v1/animalitos <- POST
    // En la petición HTTP, en el cuerpo (BODY) Mandarán un JSON. Ese JSON conviértelo en automático a un NuevoAnimalitoRestDTOv1
    public ResponseEntity<AnimalitoRestDTOv1> nuevoAnimal(@RequestBody NuevoAnimalitoRestDTOv1 nuevoAnimal){
        // Al ejecutarse la función, devolverá un objeto JAVA de tipo AnimalitoRestDTOv1... y tu Spring, ya la conviertes en un JSON para mandarlo de vuelta
        // Casi... Lo que devolvemos es un ResponseEntity, que en el cuerpo (BODY) del request devolverá un JSON con la estructura de AnimalitoRestDTOv1
        // Ese ResponseEntity es lo que antiguamente era un HttpServletResponse
        // El ResponseEntity, además de el cuerpo lleva otros datos, el principal: CODIGO DE RESPUESTA HTTP DEL SERVIDOR: 200, 201, 404,...
        try {
            NuevoAnimalitoDTO nuevoAnimalServicio = mapper.nuevoAnimalitoRestDTOv1_2_NuevoAnimalitoDTO(nuevoAnimal);
            AnimalitoDTO devuelto = servicio.nuevoAnimal(nuevoAnimalServicio);
            AnimalitoRestDTOv1 paraDevolverAqui = mapper.animalitoDTO2AnimalitoRestDTOv1(devuelto);
            return new ResponseEntity<>(paraDevolverAqui, HttpStatus.CREATED); // 201: CREATED
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400: BAD_REQUEST
        }
    }

    @GetMapping("/animalitos") // Se concatena la ruta con la de arriba
    public ResponseEntity<List<AnimalitoRestDTOv1>> buscarTodos(){
        return new ResponseEntity<>(
                servicio.buscarTodos().stream().map(mapper::animalitoDTO2AnimalitoRestDTOv1).collect(Collectors.toList()),
                HttpStatus.OK); // Código 200
    }

    //@Secured("USER")
    @PreAuthorize("isAuthenticated()") // dentro tenemos que dar la condición de preautorización. Ahi dentro Spring tiene un lenguaje propio llamado SPEL
    @GetMapping("/animalitos/{id}") // Se concatena la ruta con la de arriba
    // https://servidor/api/v1/animalitos/172634 <- GET
    public ResponseEntity<AnimalitoRestDTOv1> recuperarPorId(@PathVariable("id") Long id){
        Optional<AnimalitoDTO> posibleAnimalitoEncontrado = servicio.recuperarPorId(id);
        // 400: BAD_REQUEST
        /*
        if(posibleAnimalitoEncontrado.isPresent())
            return new ResponseEntity<>(mapper.animalitoDTO2AnimalitoRestDTOv1(posibleAnimalitoEncontrado.get()), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 400: BAD_REQUEST
        */
        return posibleAnimalitoEncontrado.map(animalitoDTO ->
                        new ResponseEntity<>(mapper.animalitoDTO2AnimalitoRestDTOv1(animalitoDTO), HttpStatus.OK)
                ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
