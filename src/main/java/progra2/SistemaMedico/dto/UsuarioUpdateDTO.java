package progra2.SistemaMedico.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import progra2.SistemaMedico.model.enums.UserRol;

@Data
public class UsuarioUpdateDTO {
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @Size(max = 150, message = "Los apellidos no pueden exceder 150 caracteres")
    private String apellidos;

    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Pattern(regexp = "^\\d{8,20}$", message = "El teléfono debe contener entre 8 y 20 dígitos")
    private String telefono;

    private Boolean esActivo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRol rol;

    // Solo se valida si se decide cambiar la contraseña
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;
}
