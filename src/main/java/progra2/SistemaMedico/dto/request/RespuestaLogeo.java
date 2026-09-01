package progra2.SistemaMedico.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import progra2.SistemaMedico.model.enums.UserRol;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RespuestaLogeo {

    // esta clase devuelve el token y el tipo de token y los datos basicos del usuario en dado caso el logeo es exitoros
    private String token;
    private String tipoDeToken = "Bearer";
    private String username;
    private Long usuarioId;
    private String dpi;
    private String nombre;
    private String apellidos;
    private Boolean esAdministrador;
    UserRol rol;

}
