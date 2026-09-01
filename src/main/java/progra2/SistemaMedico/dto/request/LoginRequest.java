package progra2.SistemaMedico.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    // en esta clase se recibe la informacion desde postman o el ciente  para loguearse
    @NotBlank(message = "Este campo es obligatorio")
    private String username;

    @NotBlank(message = "Este campo es obligatorio")
    private String password;




}
