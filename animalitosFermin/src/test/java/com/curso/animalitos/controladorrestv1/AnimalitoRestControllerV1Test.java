package com.curso.animalitos.controladorrestv1;

import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import com.curso.animalitos.servicioimpl.AplicacionDePruebaServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
// Ahora se abrirá un tomcat... En qué puerto? Toma uno aleatorio que haya disponible! (2)
// La decisión de abrir tomcat la toma Spring, ya que estoy configurando un controlador REST (2)
@SpringBootTest(classes = AplicacionDePruebaControladorV1.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Spring, quiero un cliente HTTP para atacar al tomcat que hayas levantado... Tu sabrás en qué puerto está. Yo npi! (1)
class AnimalitoRestControllerV1Test {

    private MockMvc clienteHttp; // (1)

    @MockBean
    private AnimalitoService servicioDeMentirijilla;

    public AnimalitoRestControllerV1Test(@Autowired MockMvc clienteHttp){ // Spring, dame una instancia de un cliente HTTP
        this.clienteHttp=clienteHttp;
    }


    @Test
    void recuperarPorIdExistente() throws Exception {

        // DADO
        // - Que tengo un servicio de mentirijilla que cuando le pidan el animalito 123 devolverá:
        //   [Firulais, Perro, Nacido ahora]
        Long id = 123L;
        String nombre = "Firulais";
        String tipo = "perro";
        LocalDateTime fecha = LocalDateTime.now();
        AnimalitoDTO loQueDevuelveElServicio = new AnimalitoDTO();
        loQueDevuelveElServicio.setId(id);
        loQueDevuelveElServicio.setNombre(nombre);
        loQueDevuelveElServicio.setTipo(tipo);
        loQueDevuelveElServicio.setFechaNacimiento(fecha);
        when(servicioDeMentirijilla.recuperarPorId(anyLong())).thenReturn(Optional.of(loQueDevuelveElServicio));
        // - Un controlador REST Funcionando dentro de un servidor de aplicaciones que esté arrancado (2)
        // - Un cliente HTTP para conectarme a ese servidor de aplicaciones (1)
        // CUANDO
        // - Lance una petición HTTP GET en mi cliente al servidor de aplicaciones en la ruta /api/v1/animalitos/123
        ResultActions resultado = clienteHttp.perform(MockMvcRequestBuilders.get("/api/v1/animalitos/"+id));
        // ENTONCES
        // - Se debe haber llamado al Servicio con el id que me han pasado
        // - Devuelva una respuesta http con código de estado: 200
        resultado.andExpect(status().isOk()); // Son reglas Assertions especiales que nos regala Spring para trabajar con Servicios WEB
        // - Y en el cuerpo de esa respuesta tenga un JSON que tenga los datos de firulais
        resultado.andExpect(content().contentType(MediaType.APPLICATION_JSON)); // MimeType (Tipo de dato en la respuesta)
        /*
            {
                "id": 123,
                "nombre": "Firulais",
                "tipo": perro,
                "fechaNacimiento": ""
            }
        */ // VAMOS A TRABAJAR CON JSON PATH. Es un concepto equivalente a XPATH (Un estándar del W3C para Buscar cosas en un XML)
        resultado.andExpect(jsonPath("$.id").value(id));
        resultado.andExpect(jsonPath("$.nombre").value(nombre));
        resultado.andExpect(jsonPath("$.tipo").value(tipo));
        resultado.andExpect(jsonPath("$.fechaNacimiento").value(fecha.toString()));
    }

    // CUCUMBER !
    @Test

    void recuperarPorIdNoExistente() throws Exception {

        // DADO
        // - Que tengo un servicio de mentirijilla que no tiene animales
        // - Un controlador REST Funcionando dentro de un servidor de aplicaciones que esté arrancado
        // - Un cliente HTTP para conectarme a ese servidor de aplicaciones
        // CUANDO
        // - Lance una petición HTTP GET en mi cliente al servidor de aplicaciones en la ruta /api/v1/animalitos/123
        // ENTONCES
        // - Se debe haber llamado al Servicio con el id que me han pasado
        // - Devuelva una respuesta http con código de estado: 404
        ResultActions resultado = clienteHttp.perform(MockMvcRequestBuilders.get("/api/v1/animalitos/123452"));
        resultado.andExpect(status().isNotFound());
    }
}