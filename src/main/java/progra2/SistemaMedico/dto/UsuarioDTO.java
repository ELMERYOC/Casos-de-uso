package progra2.SistemaMedico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import progra2.SistemaMedico.model.enums.UserRol;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioDTO {
    private Long id;
    private UserRol rol;


    @NotNull(message = "El campo DPI es obligatorio. Por favor, ingrese su número de DPI.")
    private String dpi;

    @NotNull(message = "El campo NIT es obligatorio.")
    @Size(min = 8, max = 9, message = "El NIT debe contener entre 8 y 9 caracteres. Usted ingresó {value} caracteres.")
    private String nit;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre completo no puede exceder 150 caracteres")
    private String nombre;

    @NotBlank(message = "los dos apellidos son obligatorios")
    @Size(max = 150, message = "los apellidos completo no puede exceder 150 caracteres")
    private String apellidos;

    @NotBlank(message = "Username obligatorio")
    @Size(max = 15, message = "maximo 15 caracteres" )
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;


    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no puede exceder 100 caracteres")
    private String email;

    @Pattern(regexp = "^\\d{8,20}$", message = "El teléfono debe contener entre 8 y 20 dígitos")
    private String telefono;

    @Size(max = 50, message = "El número de seguro no puede exceder 50 caracteres")
    private String numeroSeguro;

    private Boolean esActivo;

    private Boolean esAdministrador;


}
