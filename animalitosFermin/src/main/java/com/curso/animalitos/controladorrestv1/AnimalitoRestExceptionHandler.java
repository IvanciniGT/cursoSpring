package com.curso.animalitos.controladorrestv1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * Esta clase maneja las excepciones solo para el controlador AnimalitoRestControllerV1.
 * Usamos @ControllerAdvice y @ExceptionHandler para capturar y devolver respuestas personalizadas en caso de error.
 */
@ControllerAdvice(basePackageClasses = AnimalitoRestControllerV1.class)
//@ControllerAdvice // Aplicaría a todos los controladores que tuviera en mi app
public class AnimalitoRestExceptionHandler {

    /**
     * Manejo global de excepciones. Cualquier excepción no específica que ocurra en AnimalitoRestControllerV1
     * será capturada por este método y devolverá un error 500 (Error interno del servidor).
     *
     * @param ex Excepción lanzada
     * @param request Información de la solicitud
     * @return ResponseEntity con información del error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarExcepcionGlobal(Exception ex, WebRequest request) {
        // Registrar la excepción en la consola
        ex.printStackTrace();

        // Crear el objeto de respuesta con los detalles del error
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Error interno del servidor",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Retornar una respuesta con el código de estado 500
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejo de excepciones específicas como IllegalArgumentException o DataIntegrityViolationException.
     * Se devolverá un error 400 (Argumento inválido) en caso de que ocurran.
     *
     * @param ex Excepción lanzada
     * @param request Información de la solicitud
     * @return ResponseEntity con información del error
     */
    @ExceptionHandler({IllegalArgumentException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponseDTO> manejarExcepcionArgumentoInvalido(Exception ex, WebRequest request) {
        // Crear el objeto de respuesta con los detalles del error
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Argumento inválido",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());

        // Retornar una respuesta con el código de estado 400
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponseDTO> errorPrivilegios(Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // DTO para manejar los detalles del error en la respuesta HTTP
    @Data
    @AllArgsConstructor
    public static class ErrorResponseDTO {
        private String mensaje;
        private String detalles;
        private int codigo;
    }
}
