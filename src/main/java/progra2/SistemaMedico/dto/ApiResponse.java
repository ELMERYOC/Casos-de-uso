package progra2.SistemaMedico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Integer statusCode;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .statusCode(200)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, Integer statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .statusCode(statusCode)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class SolicitudCitaDTO {
        @NotNull(message = "la sucursal es obligatoria")
        private Long sucursalId;

        @NotNull(message = "La especialidad es obligatoria")
        private Long especialidadId;

        @NotNull(message = "El medico es obligatorio")
        private Long medicoId;

        @NotNull(message = "La fecha y hora son obligatorias")
        private LocalDateTime fechaHora;

        @NotNull(message = "El motivo de la cita es obligatorio")
        @Size(min = 10, max = 2000, message = "El metivo debe tener menos de 2000 caracteres")
        private String motivoCita;

    }
}