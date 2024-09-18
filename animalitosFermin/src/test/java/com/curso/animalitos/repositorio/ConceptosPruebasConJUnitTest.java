package com.curso.animalitos.repositorio;


// Vamos a usar la versión 5 de JUnit... La última

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConceptosPruebasConJUnitTest {

    @BeforeAll // Una funcion que ejecuto antes de Que comiencen LAS PRUEBAS
    static void antesDeTodas(){
        System.out.println("COMENZAMOS");
    }
    @AfterAll  // Una funcion que ejecuto después de Queacaben TODAS LAS PRUEBAS
    static void despuesDeTodas(){
        System.out.println("ACABAMOS");
    }
    @BeforeEach
    void antesDeCadaUna(){
        System.out.println("COMENZAMOS UNA PRUEBA");
        // Crea un animalito nuevo que lo necesito
    }
    @AfterEach
    void despuesDeCadaUna(){
        System.out.println("ACABAMOS UNA PRUEBA");
        // Limpia ciertos datos... para asegurar que no dejo mierda
    }
    // Estos hooks son muy utiles y los podemos usar sin problema


    // Por defecto, no tengo control de cómo JUNIT ejecuta las pruebas (en que orden)
    // Lo puedo forzar: usando @Order en cada test y @TestMethodOrder(MethodOrderer.OrderAnnotation.class) en la clase
    // ESTO ES UNA MIERDA !!! ROMPE LA INDEPENDENCIA DE LOS TEST.
    // MAS QUE ROMPERLA , la pregunta es PORQUE NECESITO ASEGURAR UN ORDEN? Qué pasa que si no lo aseguro no se ejecutan bien?
    // Entonces es que no son INDEPENDIENTES.
    // Si lo fueran el orden me da igual!
    @Order(2)
    @Test
    @DisplayName("Probar a sumar 2 números")
    void probarASumar2NumerosTest(){
        System.out.println("UNOS ");
        // Dado (Given) - Configuro un contexto para la prueba
        int numeroA= 5;
        int numeroB = 17;
        int resultadoEsperado = 22;
        // Cuando (When) - Ejecuto lo que quiero probar
        int resultadoObtenido = numeroA + numeroB;
        // Entonces (Then) - Compruebo los resultados
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido); // = GUAY!
        //System.out.println("He obtenido de la suma: " + resultadoObtenido + " Y debería haberse obtenido: "+ resultadoEsperado); = MIERDA !
    }
    @Order(1)
    @Test
    @DisplayName("Probar a sumar otros 2 números")
    void probarASumar2OtrosNumerosTest(){
        System.out.println("OTROS");
        int numeroA= 5;
        int numeroB = 11;
        int resultadoEsperado = 16;
        // Cuando (When) - Ejecuto lo que quiero probar
        int resultadoObtenido = numeroA + numeroB;
        // Entonces (Then) - Compruebo los resultados
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido); // = GUAY!
        //System.out.println("He obtenido de la suma: " + resultadoObtenido + " Y debería haberse obtenido: "+ resultadoEsperado); = MIERDA !

    }
}
// Quién ejecuta la prueba? JUnit
// Junit es quien crea una instancia de esta clase
// Junit es quien llama a la función probarASumar2NumerosTest (ya que está anotada con @Test)
// JUnit es el que verifica la aserción.. y muestra el resultado

// Quién llama a JUnit? "java ....  JUnit MiClaseDePruebas" ???? IntelliJ
// Quién se lo ha pedido a IntelliJ? Vosotros

// Me vale esto? Es lo que quiero? NO ME VALE.
// NO ME FIO UNA MIERDA DE LAS PRUEBAS QUE SE HACEN EN LAS MAQUINAS DE LOS DESARROLLADORES. ESTAN MALEADAS !!!!!

// Estas pruebas las quiero ejecutar en un entorno LIMPIO (Entorno de preproducción o TEST)
// Y en ese entorno tendrémos IntelliJ? NOP... y eclipse??? NOP...
// Y Entonces? Qué tengo en ese entorno? MAVEN
// Y podré hacer que maven ejecute las pruebas, pidiéndoselo maven a su vez al plugin SUREFIRE que a su vez se lo pide a JUNIT
// Y si quiero podré automatizar la LLAMADA a MAVEN... Eso es lo que hace JENKINS
// JENKINS -> MAVEN -> SUREFIRE -> JUNIT -> Ejecute las pruebas
// ESTO SI !!!!!
// A Junit se la pela como se llamen los ficheros de pruebas. A SUREFIRE NO... las clases de prueba deben acabar con "Test"

// Surefire es quien ejecuta las pruebas... Es quien llama a JUnit.
// Me viene en el pom directamente El plugin SUREFIRE... Qué versión? AMIGO !!!
// Depende de la versión de maven que tenga instalada
// Si uso una versión de surefire vieja... lo mismo no me reconoce las pruebas de JUnit 5... ya que venía preparada para trabajar con JUnit 4
// PROBLEMON !
// Es más, tengo control de qué versión de maven habrá instalada en el servidor de pruebas? Entorno de pruebas? NO
// Entonces, qué garantía tengo de que las pruebas allí se vayan a ejecutar? NINGUNA
// Pues necesito asegurar que se ejecutarán.