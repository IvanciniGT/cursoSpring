import java.util.function.*;
import java.util.*;

// Este paquete NUEVO de java 1.8 (hace 10 años)  tiene INTERFACES que nos
// permiten definir tipos de datos que representan funciones: INTERFACES FUNCIONALES
// Por ejemplo:
// - Function<T,R>    Es una función que recibe un argumento de tipo T y devuelve un dato de tipo R
//      Los function tienen una función apply que me permite ejecutar la función que representan
// - Consumer<T>      Es una función que recibe un argumento de tipo T y no devuelve nada
//      Aquí entran los setters
//      Los consumer tienen una función accept que me permite ejecutar la función que representan
// - Supplier<R>      Es una función que no recibe argumentos y devuelve un dato de tipo R
//      Aquí entran los getters
//      Los supplier tienen una función get que me permite ejecutar la función que representan
// - Predicate<T>     Es una función que recibe un argumento de tipo T y devuelve un booleano
//    Aquí entran las funciones de tipo isXXX() o hasXXX()
//    Los predicate tienen una función test que me permite ejecutar la función que representan
// Hay otros 40 interfaces funcionales en el paquete java.util.function
// - BiFunction<T,U,R>  Es una función que recibe dos argumentos de tipo T y U y devuelve un dato de tipo R
public class ProgramacionFuncional {
    
    public static void saluda(String nombre) {
        System.out.println("Hola " + nombre);
    }

    public static void main(String[] args) {
        Consumer<String> miFuncion = ProgramacionFuncional::saluda; // En JAVA 1.8 sale un operador nuevo, el operador ::
                                             // Me permite referenciar una función en un contexto
        miFuncion.accept("Ivan"); // La función accept, me permite ejecutar la función que referencio, pasándole un argumento

        ProgramacionFuncional ejemplo = new ProgramacionFuncional();
        ejemplo.imprimirSaludo(ejemplo::generarSaludoFormal, "Ivan");

        // Hay un operador adicional que sale en java 1.8, el operador ->, que permite crear expresiones lambda
        // Pregunta: Para qué creamos funciones habitualmente?
        // - Reutilizar código
        // - Mejorar la legibilidad y mantenibilidad del código
        // Ahora tenemos un tercer motivo, al usar programación funcional: 
        // - Para poder pasar la función como argumento a otra función que requiere una función
        // Y hay veces que esa función que creamos, no la queremos reutilizar en otro sitio
        // Y hay veces que el tener esa función definida en otro sitio (clase... 200 lineas más arriba...)
        // Me complica la legibilidad y la mantenibilidad del código
        // Y entonces es cuando las expresiones lambda son útiles

        // Qué es una lambda? Una expresión
        // Qué es una expresión?
        // String texto ="HOLA"; // Statement (intrucción, orden, sentencia=FRASE)
        // int numero = 5+5;     // Otro statement
        //              /// EXPRESION: Un trozo de código que devuelve un valor
        // Por lo tanto una lambda es un trozo de código que devuelve un valor... Qué valor?
        // UNA FUNCIÓN ANONIMA DEFINIDA DENTRO DE LA PROPIA EXPRESION.
        // ES UNA FORMA ALTERNATIVA DE DECLARAR UNA FUNCIÓN EN EL LENGUAJE
        Function<String,String> miFuncion2 = ejemplo::generarSaludoFormal;
        Function<String,String> miFuncion3 = (String nombre) -> { // Declarar una función que..
            return "Qué pasa " + nombre + "!!!!"; // El tipo de vuelta se infiere de la operación
        };
        System.out.println(miFuncion3.apply("Ivan"));

        Function<String,String> miFuncion4 = (nombre) -> { // Declarar una función que..
                                            // El tipo de dato de entrada si se infiere de la variable
            return "Qué pasa " + nombre + "!!!!"; // El tipo de vuelta se infiere de la operación
        };


        Function<String,String> miFuncion5 = nombre -> {
            return "Qué pasa " + nombre + "!!!!";
        };

        Function<String,String> miFuncion6 = nombre -> "Qué pasa " + nombre + "!!!!";

        ejemplo.imprimirSaludo(ejemplo::generarSaludoFormal, "Ivan");
        ejemplo.imprimirSaludo(ejemplo::generarSaludoInformal, "Ivan");
        ejemplo.imprimirSaludo(miFuncion6, "Ivan");
        ejemplo.imprimirSaludo(nombre -> "Qué pasa " + nombre + "!!!!", "Ivan");

        // Fijaros:
        List<String> animales = new ArrayList<>();
        animales.add("Perro");
        animales.add("Gato");
        animales.add("Tiburón");
        // Prejava 1.5, si queria hacer un bucle iterando por los tiburones
        for (int i = 0; i < animales.size(); i++) {
            System.out.println(animales.get(i));
        }
        // Pre Java 1,8 (con la aparición de los Iterables en Java 1.5)
        for (String animal : animales) {
            System.out.println(animal);
        }
        // Post Java 1,8, con la aparición de la programación funcional
        animales.forEach(System.out::println); // Toda colección en Java desde 1.,8 tiene el método forEach, que acepta un consumer<T>
        animales.forEach( animal -> System.out.println(" - " + animal) ); // Toda colección en Java desde 1.,8 tiene el método forEach, que acepta un consumer<T>


    }

    public String generarSaludoFormal(String nombre) {
        return "Estimado " + nombre;
    }

    public String generarSaludoInformal(String nombre) {
        return "Hola " + nombre;
    }

    public void imprimirSaludo(Function<String, String> funcionGeneradoraDeSaludos, String nombre) {
        System.out.println(funcionGeneradoraDeSaludos.apply(nombre));
    }
}
