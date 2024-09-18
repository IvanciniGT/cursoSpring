package com.curso.animalitos.servicioimpl;

import com.curso.animalitos.AplicacionDePrueba;
import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.repositorio.repository.AnimalitoRepositorio;
import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicio.dtos.NuevoAnimalitoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AplicacionDePrueba.class)
@ExtendWith(SpringExtension.class)
class AnimalitoServiceImplTest {

    private final AnimalitoService servicio;

    // Esta anotación:
    // - le pide a mockito que genere un objeto de pruebas extendiendo una interfaz
    // - Y además, configura en Spring que si alguien pide una instancia de esa interfaz: AnimalitoRepositorio
    //   que le entregue una de la clase que genera Mockito, aunque haya otras implementaciones
    //   Hace lo mismo que hemos hecho a mano con @Primary
    @MockBean
    private AnimalitoRepositorio repositorioDeMentirijilla;
    // Creo un capturador de argumentos de llamadas a nuestro mock (repo de mierda!)
    // Esto es un sitio para alojar argumentos de tipo AnimalitoEntity
    // Pero ahora he de usar esto.
    @Captor
    private ArgumentCaptor<AnimalitoEntity> entidad;

    public AnimalitoServiceImplTest(@Autowired AnimalitoService servicio){
        this.servicio=servicio;
    }

    @Test
    @DisplayName("Nuevo animalito con datos GUAYS")
        // Tipo de prueba que estamos definiendo: Funcional / Unitaria
    void nuevoAnimalDatosGuays() {
        // Dado
        // Los datos de un animalito guays (NuevoAnimalitoDTO)
        NuevoAnimalitoDTO nuevoAnimalitoGuay = new NuevoAnimalitoDTO();
        nuevoAnimalitoGuay.setNombre("Firulais");
        nuevoAnimalitoGuay.setTipo("Perro");
        nuevoAnimalitoGuay.setFechaNacimiento(LocalDateTime.now());
        // Y un repo de mentirijilla trucao! CONFIGURO MOCKITO
        AnimalitoEntity datoQueDevolveraElRepoDeMentirijilla = AnimalitoEntity.builder()
                .nombre(nuevoAnimalitoGuay.getNombre())
                .tipo(nuevoAnimalitoGuay.getTipo())
                .fechaNacimiento(nuevoAnimalitoGuay.getFechaNacimiento())
                .id(-1928462824L).build();
        when(repositorioDeMentirijilla.save(any(AnimalitoEntity.class))).thenReturn(datoQueDevolveraElRepoDeMentirijilla);
        // Cuando
        // Llame a la función nuevoAnimalito del Servicio con esos datos
        AnimalitoDTO devuelto = servicio.nuevoAnimal(nuevoAnimalitoGuay);
        // Entonces
        // - Me devuelve un AnimalitoDTO
        assertNotNull(devuelto);
        // - El AnimalitoDTO tiene los datos del NuevoAnimalitoDTO
        assertEquals(nuevoAnimalitoGuay.getNombre(), devuelto.getNombre());
        assertEquals(nuevoAnimalitoGuay.getTipo(), devuelto.getTipo());
        assertEquals(nuevoAnimalitoGuay.getFechaNacimiento(), devuelto.getFechaNacimiento());
        // - Me llega un id
        assertNotNull(devuelto.getId());
        // - Que debe haber sido proporcionado por el REPOSITORIO!!!!
        assertEquals(datoQueDevolveraElRepoDeMentirijilla.getId(), devuelto.getId());

        // Me aseguro que al repo le hayan pasado los datos adecuados
        verify(repositorioDeMentirijilla).save(entidad.capture());
        assertEquals(nuevoAnimalitoGuay.getNombre(), entidad.getValue().getNombre());
        assertEquals(nuevoAnimalitoGuay.getTipo(), entidad.getValue().getTipo());
        assertEquals(nuevoAnimalitoGuay.getFechaNacimiento(), entidad.getValue().getFechaNacimiento());

        // Si es una prueba UNITARIA, necesito aislarme del REPOSITORIO...
        // Es más, quizás ese repositorio NI EXISTE
        // Necesito crear mi Repositorio de Mentirijilla

        // - Compruebo que se haya SOLICITADO el envío un email?
        //   A quien? NO SE... HABRA UN REQUISITO QUE IMPONGA ESTO? subscripciones@animalitos-fermin.com
        //   Asunto? HABRA UN REQUISITO                             Nuevo animalito disponible
        //   Contenido? HABRA UN REQUISITO                          Tenemos a la venta a <NOMBRE> que es un <TIPO> muy bonito. Ven a verlo!


        // El servicio llamará al AnimalitoRepositorio.nuevoAnimalito
        // Se le devuelve un AnimalitoEntity... que tendrá un ID
        // El servicio debe coger ese ID y meterlo en un AnimalitoDTO que es lo que se nos entrega como respuesta de esta función del servicio

    }

}