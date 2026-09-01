package progra2.SistemaMedico.servicios.serviceImpl;

import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.request.RespuestaLogeo;
import progra2.SistemaMedico.dto.request.LoginRequest;

public interface IAutenticacionServicio {

    // Autenticacion del usuario y respuesta en DTO
    RespuestaLogeo login(LoginRequest solicitudDeLogueo);
    // Registro de un nuevo usuario en el sistema
    RespuestaLogeo registrar (UsuarioDTO dto);

}
