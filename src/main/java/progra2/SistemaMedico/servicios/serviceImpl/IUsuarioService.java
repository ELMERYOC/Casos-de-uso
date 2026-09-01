package progra2.SistemaMedico.servicios.serviceImpl;


import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.UsuarioUpdateDTO;
import progra2.SistemaMedico.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<UsuarioDTO> listarUsuarios();
    Optional<UsuarioDTO> obtenerPorId(Long id);
    Optional<UsuarioDTO> obtenerPorDpi(String dpi);
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO actualizarUsuario(Long id, UsuarioUpdateDTO usuarioDTO);
    void eliminarUsuario(Long id);
//    UsuarioDTO registrarUsuarioExterno(UsuarioDTO usuarioDTO) throws Exception;


}
