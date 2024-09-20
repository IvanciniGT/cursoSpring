package com.curso.animalitos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Este aspecto se encarga de añadir lógica transversal (cross-cutting concerns) a los mét odos de los servicios
 * dentro del paquete com.curso.animalitos.servicioimpl. Usamos AOP (Aspect-Oriented Programming) para interceptar
 * la ejecución de estos mét odos y aplicar tareas como logging, medición de tiempo y manejo de excepciones.
 */
@Aspect
@Component
public class LoggingAspect {

    // Logger de SLF4J para registrar mensajes de log
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Definimos un Pointcut reutilizable que intercepta todos los mét odos dentro del paquete
     * com.curso.animalitos.servicioimpl.
     *
     * @Pointcut: Define un "punto de corte" que especifica qué mét odos o clases deben ser interceptados.
     * En este caso, todos los mét odos (independientemente de su tipo de retorno o parámetros) dentro
     * del paquete com.curso.animalitos.servicioimpl.
     *
     * Sintaxis del Pointcut:
     * - execution(* com.curso.animalitos.servicioimpl.*.*(..)):
     *   - `*`: Representa cualquier tipo de retorno (puede ser void, int, String, etc.).
     *   - `com.curso.animalitos.servicioimpl.*`: Representa cualquier clase dentro del paquete servicioimpl.
     *   - `*.*(..)`: Representa cualquier mét odo, con cualquier nombre y cualquier número de parámetros.
     */
    @Pointcut("execution(* com.curso.animalitos.servicioimpl.*.*(..))")
    public void allServiceMethods() {
    }

    /**
     * @Before: Se ejecuta antes de que el mét odo objetivo sea invocado.
     *
     * En este caso, interceptamos todos los mét odos definidos por el Pointcut 'allServiceMethods'
     * y registramos un mensaje antes de que el mét odo sea ejecutado.
     *
     * @param joinPoint: Proporciona información sobre el mét odo interceptado, como su nombre y argumentos.
     */
    @Before("execution(* com.curso.animalitos.servicioimpl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        // joinPoint.getSignature().getName(): Obtiene el nombre del mét odo que va a ser ejecutado.
        logger.info("Antes de ejecutar: " + joinPoint.getSignature().getName());
    }

    /**
     * @After: Se ejecuta después de que el mét odo objetivo haya sido invocado, sin importar si este
     * ha tenido éxito o si ha lanzado una excepción.
     *
     * Usamos este consejo para registrar un mensaje después de la ejecución de cualquier mét odo del
     * paquete servicioimpl, independientemente del resultado.
     *
     * @param joinPoint: Proporciona información sobre el mét odo interceptado.
     */
    @After("allServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        // Se registra el nombre del mét odo que fue ejecutado, sin importar su resultado.
        logger.info("Después de ejecutar: " + joinPoint.getSignature().getName());
    }

    /**
     * @AfterReturning: Se ejecuta solo cuando el mét odo ha sido ejecutado correctamente (sin lanzar excepciones).
     *
     * Usamos este consejo para registrar un mensaje cuando un mét odo del paquete servicioimpl termina con éxito.
     *
     * @param joinPoint: Proporciona información sobre el mét odo interceptado.
     */
    @AfterReturning("allServiceMethods()")
    public void logAfterReturning(JoinPoint joinPoint) {
        // Se registra el nombre del mét odo solo si este ha sido ejecutado exitosamente.
        logger.info("Método exitoso: " + joinPoint.getSignature().getName());
    }

    /**
     * @Around: Este consejo se ejecuta antes y después de la ejecución del mét odo interceptado, envolviéndolo.
     *
     * Aquí usamos @Around para medir el tiempo que tarda en ejecutarse cada mét odo del paquete servicioimpl.
     *
     * @param joinPoint: Representa el mét odo que está siendo ejecutado y permite invocarlo explícitamente.
     * @return El resultado del mét odo ejecutado.
     * @throws Throwable: Excepciones que puedan ser lanzadas durante la ejecución del mét odo.
     */
    @Around("allServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Capturamos el tiempo inicial antes de la ejecución del mét odo.
        long startTime = System.currentTimeMillis();

        // El mét odo joinPoint.proceed() invoca el mét odo interceptado.
        Object result;
        try {
            result = joinPoint.proceed(); // Ejecuta el mét odo objetivo
        } finally {
            // Calculamos el tiempo transcurrido y lo registramos.
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info(joinPoint.getSignature() + " ejecutado en " + timeTaken + " ms");
        }

        // Retornamos el valor devuelto por el mét odo interceptado.
        return result;
    }

    /**
     * @AfterThrowing: Se ejecuta si el mét odo interceptado lanza una excepción.
     *
     * Este consejo se usa para capturar y manejar excepciones lanzadas por mét odos del paquete servicioimpl.
     *
     * @param joinPoint: Proporciona información sobre el mét odo que lanzó la excepción.
     * @param error: La excepción lanzada por el mét odo interceptado.
     */
    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "error")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
        // Se registra el nombre del mét odo y el mensaje de error si el mét odo lanzó una excepción.
        logger.error("Error en mét odo: " + joinPoint.getSignature().getName() + ", Error: " + error.getMessage());
    }

}
