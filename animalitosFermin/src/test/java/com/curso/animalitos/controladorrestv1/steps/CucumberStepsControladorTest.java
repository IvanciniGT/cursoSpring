package com.curso.animalitos.controladorrestv1.steps;

import com.curso.animalitos.controladorrestv1.AplicacionDePruebaControladorV1;
import com.curso.animalitos.servicio.AnimalitoService;
import com.curso.animalitos.servicio.dtos.AnimalitoDTO;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Este fichero incluye una suite de pruebas de JUNIT... Es ejecutable desde JUnit
@Suite
// Le indico a JUNIT que use cucumber
@IncludeEngines("cucumber") // Este cucumber lo puedo poner al haber introducido la dependencia de Cucumber-JUNIT-Platform-Engine
// Quien debe ser el que debe poder ejecutar los test: A quien le vamos a pedir que ejecute los test? MAVEN
// Y Maven se lo pide a? El plugin SUREFIRE
// Y el plugins surefire sabe hablar solo con JUNIT
// Es decir, JUNIT debe ser capaz de ejecutar esta prueba... Pero no está escrita en JUNIT... está escrita con Cucumber.
// Para eso, en JUnit 5 surge el concepto de JUNIT-PLATFORM.
// Junit se convierte en un integrador de motores de pruebas
// Podemos instalar el JUnit Plantform -> Motor de pruebas compatible que ejecute pruebas
// Cucumber no es compatible con JUnit Platform
// Pero la gente de cucumber nos da una libreria para compatibilizarlo: Cucumber-Junit-platform

@SelectClasspathResource("features") // Junit, buscar recursos en esta ruta, y pásalos a Cucumber

@ExtendWith(SpringExtension.class) // A JUnit le indico que argumentos del contructor pueden ser provistos por Spring
@SpringBootTest(classes = AplicacionDePruebaControladorV1.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Spring, quiero un cliente HTTP para atacar al tomcat que hayas levantado... Tu sabrás en qué puerto está. Yo npi! (1)
@CucumberContextConfiguration // Permite a Cucumber pedir dependencias a Spring
//. cucumber-spring nos da esta dependencia, para que podamos usar @Autowire en ficheros instanciados por Cucumber
public class CucumberStepsControladorTest {

    @Autowired // En este caso, que Cucumber puede ccrear una instancia de la clase, cucumber no admite que la clase reciba parametros. No nos queda opción
    private MockMvc clienteHttp; // (1)

    private String rutaPeticion;
    private String metodoPeticion;
    private ResultActions resultado;
    private AnimalitoDTO animalitoEnServicio;

    @MockBean
    private AnimalitoService servicioDeMentirijilla;

    @Dado("Que tengo un servicio de animalitos en el que no existe el animalito con id {int}")
    public void federico(Integer id) {
        // Como es un mock... no tengo nada que hacer. Lo puedo hacer explicito si quiero
        when(servicioDeMentirijilla.recuperarPorId((long)id)).thenReturn(Optional.empty());
    }

    @Cuando("Hago una petición REST a {string} por método {string}")
    public void toyota(String ruta, String metodo) {
        this.rutaPeticion   = ruta;
        this.metodoPeticion = metodo;
    }

    @Cuando("Pero en la subruta {int}")
    public void limon(Integer subruta) {
        this.rutaPeticion   += subruta;
    }

    @Entonces("Recibo un código de respuesta {string}")
    public void recibo_un_código_de_respuesta(String codigoRespuestaHttp) throws Exception {
        if(metodoPeticion.equalsIgnoreCase("get"))
            resultado = clienteHttp.perform(MockMvcRequestBuilders.get(rutaPeticion));

        switch(codigoRespuestaHttp.toUpperCase()){
            case "ENCONTRADO":
                resultado.andExpect(status().isOk());
                break;
            case "NO ENCONTRADO":
                resultado.andExpect(status().isNotFound());
                break;
        }

    }

    @Dado("Que tengo un animalito en el servicio de animalito con id {int}")
    public void que_tengo_un_animalito_en_el_servicio_de_animalito_con_id(Integer id) {
        animalitoEnServicio = new AnimalitoDTO();
        animalitoEnServicio.setId((long)id);
        when(servicioDeMentirijilla.recuperarPorId((long)id)).thenReturn(Optional.of(animalitoEnServicio));
    }

    @Dado("el animalito tiene por {string}: {string}")
    public void el_animalito_tiene_por(String campo, String valor) {
        if(campo.equalsIgnoreCase("nombre"))
            animalitoEnServicio.setNombre(valor);
        else if(campo.equalsIgnoreCase("tipo"))
            animalitoEnServicio.setTipo(valor);
    }

    @Dado("el animalito ha nacido hoy")
    public void el_animalito_ha_nacido_hoy() {
        animalitoEnServicio.setFechaNacimiento(LocalDate.now().atStartOfDay());
    }

    @Entonces("Se me entrega un JSON")
    public void se_me_entrega_un_json() throws Exception {
        resultado.andExpect(content().contentType(MediaType.APPLICATION_JSON)); // MimeType (Tipo de dato en la respuesta)
    }

    @Entonces("En el json viene el campo {string} con valor {int}")
    public void en_el_json_viene_el_campo_con_valor(String campo, Integer valor) throws Exception {
        resultado.andExpect(jsonPath("$."+campo).value(valor));
    }

    @Entonces("En el json viene el campo {string} con valor {string}")
    public void en_el_json_viene_el_campo_con_valor(String campo, String valor) throws Exception {
        resultado.andExpect(jsonPath("$."+campo).value(valor));
    }

}
