package com.curso.animalitos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Este aspecto se encarga de añadir lógica transversal (cross-cutting concerns) a los métodos de los servicios
 * dentro del paquete com.curso.animalitos.servicioimpl. Usamos AOP (Aspect-Oriented Programming) para interceptar
 * la ejecución de estos métodos y aplicar tareas como logging, medición de tiempo y manejo de excepciones.
 */
@Aspect
@Component
public class LoggingAspect {

    // Logger de SLF4J para registrar mensajes de log
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Definimos un Pointcut reutilizable que intercepta todos los métodos dentro del paquete
     * com.curso.animalitos.servicioimpl.
     *
     * @Pointcut: Define un "punto de corte" que especifica qué métodos o clases deben ser interceptados.
     * En este caso, todos los métodos (independientemente de su tipo de retorno o parámetros) dentro
     * del paquete com.curso.animalitos.servicioimpl.
     *
     * Sintaxis del Pointcut:
     * - execution(* com.curso.animalitos.servicioimpl.*.*(..)):
     *   - `*`: Representa cualquier tipo de retorno (puede ser void, int, String, etc.).
     *   - `com.curso.animalitos.servicioimpl.*`: Representa cualquier clase dentro del paquete servicioimpl.
     *   - `*.*(..)`: Representa cualquier método, con cualquier nombre y cualquier número de parámetros.
     */
    @Pointcut("execution(* com.curso.animalitos.servicioimpl.*.*(..))")
    public void allServiceMethods() {
        // Este método solo actúa como un alias para el Pointcut, no se ejecuta realmente.
    }

    /**
     * @Before: Se ejecuta antes de que el método objetivo sea invocado.
     *
     * En este caso, interceptamos todos los métodos definidos por el Pointcut 'allServiceMethods'
     * y registramos un mensaje antes de que el método sea ejecutado.
     *
     * @param joinPoint: Proporciona información sobre el método interceptado, como su nombre y argumentos.
     */
    @Before("allServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        // joinPoint.getSignature().getName(): Obtiene el nombre del método que va a ser ejecutado.
        logger.info("Antes de ejecutar: " + joinPoint.getSignature().getName());
    }

    /**
     * @After: Se ejecuta después de que el método objetivo haya sido invocado, sin importar si este
     * ha tenido éxito o si ha lanzado una excepción.
     *
     * Usamos este consejo para registrar un mensaje después de la ejecución de cualquier método del
     * paquete servicioimpl, independientemente del resultado.
     *
     * @param joinPoint: Proporciona información sobre el método interceptado.
     */
    @After("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        // Se registra el nombre del método que fue ejecutado, sin importar su resultado.
        logger.info("Después de ejecutar: " + joinPoint.getSignature().getName());
    }

    /**
     * @AfterReturning: Se ejecuta solo cuando el método ha sido ejecutado correctamente (sin lanzar excepciones).
     *
     * Usamos este consejo para registrar un mensaje cuando un método del paquete servicioimpl termina con éxito.
     *
     * @param joinPoint: Proporciona información sobre el método interceptado.
     */
    @AfterReturning("allServiceMethods()")
    public void logAfterReturning(JoinPoint joinPoint) {
        // Se registra el nombre del método solo si este ha sido ejecutado exitosamente.
        logger.info("Método exitoso: " + joinPoint.getSignature().getName());
    }

    /**
     * @Around: Este consejo se ejecuta antes y después de la ejecución del método interceptado, envolviéndolo.
     *
     * Aquí usamos @Around para medir el tiempo que tarda en ejecutarse cada método del paquete servicioimpl.
     *
     * @param joinPoint: Representa el método que está siendo ejecutado y permite invocarlo explícitamente.
     * @return El resultado del método ejecutado.
     * @throws Throwable: Excepciones que puedan ser lanzadas durante la ejecución del método.
     */
    @Around("allServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capturamos el tiempo inicial antes de la ejecución del método.
        long startTime = System.currentTimeMillis();

        // El método joinPoint.proceed() invoca el método interceptado.
        Object result;
        try {
            result = joinPoint.proceed(); // Ejecuta el método objetivo
        } finally {
            // Calculamos el tiempo transcurrido y lo registramos.
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info(joinPoint.getSignature() + " ejecutado en " + timeTaken + " ms");
        }

        // Retornamos el valor devuelto por el método interceptado.
        return result;
    }

    /**
     * @AfterThrowing: Se ejecuta si el método interceptado lanza una excepción.
     *
     * Este consejo se usa para capturar y manejar excepciones lanzadas por métodos del paquete servicioimpl.
     *
     * @param joinPoint: Proporciona información sobre el método que lanzó la excepción.
     * @param error: La excepción lanzada por el método interceptado.
     */
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        // Se registra el nombre del método y el mensaje de error si el método lanzó una excepción.
        logger.error("Error en método: " + joinPoint.getSignature().getName() + ", Error: " + error.getMessage());
    }

}
