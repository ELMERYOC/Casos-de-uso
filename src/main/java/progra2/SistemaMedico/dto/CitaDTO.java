package progra2.SistemaMedico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {
    private Long id;
    private String numeroCita;
    private String nombreSucursal;
    private String nombreEspecialidad;
    private String nombreMedico;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
}
