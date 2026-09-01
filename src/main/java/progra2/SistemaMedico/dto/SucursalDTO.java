package progra2.SistemaMedico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO {

    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La dirección no puede exceder los 500 caracteres")
    private String direccion;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String telefono;

    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    private Boolean activo;

    // Campos de solo lectura (Auditoría)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
