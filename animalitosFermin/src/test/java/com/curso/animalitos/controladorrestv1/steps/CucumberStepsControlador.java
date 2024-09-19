package com.curso.animalitos.controladorrestv1.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

public class CucumberStepsControlador {


    @Dado("Que tengo un servicio de animalitos en el que no existe el animalito con id {int}")
    public void que_tengo_un_servicio_de_animalitos_en_el_que_no_existe_el_animalito_con_id(Integer id) {

    }

    @Cuando("Hago una petición REST a {string} por método {string}")
    public void hago_una_petición_rest_a(String ruta, String metodo) {

    }

    @Entonces("Recibo un código de respuesta {string}")
    public void recibo_un_código_de_respuesta(String codigoRespuestaHttp) {

    }

}
