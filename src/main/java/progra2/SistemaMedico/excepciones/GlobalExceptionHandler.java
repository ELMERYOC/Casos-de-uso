package progra2.SistemaMedico.excepciones;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import progra2.SistemaMedico.dto.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. Maneja tus excepciones de negocio (como el DPI, email duplicado, etc.)
    @ExceptionHandler(ReglasDeNegocioException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleReglasDeNegocio(ReglasDeNegocioException ex) {
        ApiResponseDTO<Object> errorResponse = ApiResponseDTO.error(ex.getMessage());
        // Devuelve HTTP 400 Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 2. Maneja errores genéricos no controlados (para que no te salga el 403/500 en blanco)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGenericException(Exception ex) {
        ApiResponseDTO<Object> errorResponse = ApiResponseDTO.error("Error interno: " + ex.getMessage());
        // Devuelve HTTP 500 Internal Server Error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // 3. (Opcional) Maneja errores de @Valid (cuando falla el DTO)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleValidationExceptions(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + " | " + msg2)
                .orElse("Error de validación");

        ApiResponseDTO<Object> errorResponse = ApiResponseDTO.error(mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
