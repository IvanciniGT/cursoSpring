package com.curso.animalitos.repositorio;

import com.curso.animalitos.repositorio.entity.AnimalitoEntity;
import com.curso.animalitos.repositorio.repository.AnimalitoRepositorio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

// Antes de ejecutar estas pruebas, arranca esta aplicación en paralelo! (1)
// A ella le pedirás los beans: NUESTRO REPOSITORIO
@SpringBootTest(classes = AplicacionDePruebaRepositorio.class)
// Le indico a JUnit que algunos de los parámetros que le llegan en el constructor los debe solicitar a Spring
@ExtendWith(SpringExtension.class)
class AnimalitoEntityTest {

    private final AnimalitoRepositorio miRepositorio;

    // ESTO ES CAGADA
    // Y si el día de mañana montamos otros 20 servicios en el proyecto
    // Cual es el problema de fondo??
    // Estamos haciendo una prueba del Repositorio... y spring al arrancar está intentando crear beans de tod-o.
    // Para que quiero un bean (una instancia) del Servicio de animalitos para hacer una prueba del repositorio?
    // Por qué está tratando de crear una instancia del Servicio de Emails?
    //  Porque la necesita quién? El AnimalitosServiceImpl
    // Por qué está tratando de crear una instancia del AnimalitosServiceImpl?
    //  Porque estamos arranco una aplicación (AplicacionDePrueba) que le indica a Spring que?
    // SOLUCIONES?
    // - Crear un mock: RUINA.... Y si tengo 20 servicios???
    //   @MockBean
    //   private EmailsService servicioEmails;
    // - Mucho mejor: Mover la AplicacionDePrueba al paquete que incluye solo el repositorio
    // - Mucho mucho mejor: Que el repo tuviera su propio proyecto independiente del resto!

    // Qué parámetros? Porque podría haber 50 en el constructor.. y que Spring provea algunos y otros los provea un proveedor de parámetros de JUnit.
    public AnimalitoEntityTest(@Autowired AnimalitoRepositorio miRepositorio){ // Inyección de dependencias
                               // ÚNICO USO LEGITIMO DEL AUTOWIRED: Le indico a JUNIT que este dato se lo tiene que pedir a Spring
        this.miRepositorio=miRepositorio;
    }
    // Le hemos dicho a Spring que cuando arranque la aplicación busque Repositorios a implementar en el paquete com.curso.animalitos.repositorio? NO
    // PUES OSTION !
    // Ya se lo hemos dicho (1)... pero ahora no tenemos BBDD
    // Y necesito una BBDD para que esto arranque!
    // Cuál instalamos? H2

    @Test
    @DisplayName("Alta de un animalito con datos guays") // HAPPY PATH
    void altaGuay(){
        // Dado un animalito con datos GUAYS
        String nombre = "Firulais";
        String tipo = "perro";
        LocalDateTime fechaNacimiento = LocalDateTime.now();
        AnimalitoEntity miAnimalito = AnimalitoEntity.builder()
                .tipo(tipo)
                .nombre(nombre)
                .fechaNacimiento(fechaNacimiento).build();
        // Cuando lo grabo
        AnimalitoEntity animalitoPersistido = miRepositorio.save(miAnimalito);
        // Entonces?
        //   Se persiste en BBDD? NI DE COÑA Porque no estoy haciendo una prueba de INTEGRACION!
        //   Estoy haciendo una prueba UNITARIA. Me aislo de la BBDD.
        //   ----> Se persiste en el repositorio
        Assertions.assertNotNull(animalitoPersistido);
        // Me devuelve un animalito con id válido
        Assertions.assertNotNull(animalitoPersistido.getId());
        Assertions.assertTrue(animalitoPersistido.getId()>0);
        // Me devuelve un animalito con nombre Firulais
        Assertions.assertEquals(nombre, animalitoPersistido.getNombre());
        // Me devuelve un animalito con tipo "perro"
        Assertions.assertEquals(fechaNacimiento, animalitoPersistido.getFechaNacimiento());
        // Y con fecha de nacimiento la que toque
        Optional<AnimalitoEntity> recuperadoPorOtroLado = miRepositorio.findById(animalitoPersistido.getId());
        Assertions.assertTrue(recuperadoPorOtroLado.isPresent());
        Assertions.assertEquals(animalitoPersistido, recuperadoPorOtroLado.get());

        // DEFINIR PRUEBAS ES UN hARTE !
    }


    @Test
    @DisplayName("Alta de un animalito con datos no guays: nombre no guay") // TENGO QUE MONTAR MUCHAS COMO ESTA
    void altaNombreNulo(){
        // Dado un animalito con datos GUAYS
        String nombre = null;
        String tipo = "perro";
        LocalDateTime fechaNacimiento = LocalDateTime.now();
        AnimalitoEntity miAnimalito = AnimalitoEntity.builder()
                .tipo(tipo)
                .nombre(nombre)
                .fechaNacimiento(fechaNacimiento).build();
        // Cuando lo grabo
        /*
        try {
            AnimalitoEntity animalitoPersistido = miRepositorio.save(miAnimalito);
            // Y si llego a esta linea? MAL... no se está produciendo exception... mi código está mal
            Assertions.fail("Se debería haber lanzado una exception por usar un nombre NULL");
        }catch(Exception e){
            // Si llego aquí es que la prueba ha ido bien
        }*/

        Assertions.assertThrows(Exception.class, () -> miRepositorio.save(miAnimalito) );
    }

    @Test
    @DisplayName("Alta de un animalito con datos no guays: tipo no guay") // TENGO QUE MONTAR MUCHAS COMO ESTA
    void altaTipoNulo(){
        // Dado un animalito con datos GUAYS
        String nombre = "Firulais";
        String tipo = null;
        LocalDateTime fechaNacimiento = LocalDateTime.now();
        AnimalitoEntity miAnimalito = AnimalitoEntity.builder()
                .tipo(tipo)
                .nombre(nombre)
                .fechaNacimiento(fechaNacimiento).build();
        Assertions.assertThrows(Exception.class, () -> miRepositorio.save(miAnimalito) );
    }

    @Test
    @DisplayName("No se debe permitir actualizar el tipo de un animal") // TENGO QUE MONTAR MUCHAS COMO ESTA
    void modificarTipo(){
        String nombre = "Firulais";
        String tipo = "perro";
        LocalDateTime fechaNacimiento = LocalDateTime.now();
        AnimalitoEntity miAnimalito = AnimalitoEntity.builder()
                .tipo(tipo)
                .nombre(nombre)
                .fechaNacimiento(fechaNacimiento).build();
        // Cuando lo grabo
        AnimalitoEntity firulais = miRepositorio.save(miAnimalito);
        firulais.setTipo("GATO");
        AnimalitoEntity recuperado = miRepositorio.save(firulais);
        // TODO: CUIDADO que H" no lanza exception
    }

}
