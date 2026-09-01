package progra2.SistemaMedico.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.UsuarioUpdateDTO;
import progra2.SistemaMedico.excepciones.ReglasDeNegocioException;
import progra2.SistemaMedico.excepciones.ResourceNotFoundException;
import progra2.SistemaMedico.mapper.UsuarioMapper;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.model.enums.UserRol;
import progra2.SistemaMedico.repositorio.UsuarioRepositorio;
import progra2.SistemaMedico.servicios.serviceImpl.IUsuarioService;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private UsuarioRepositorio repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioMapper usuarioMapper;



    private void validarDpi(String dpi) {
        if (dpi == null || dpi.isBlank()) {
            throw new ReglasDeNegocioException("El campo DPI es obligatorio. Por favor, ingrese su número de DPI.");
        }
        if (!dpi.matches("\\d+")) { // Verifica que solo sean números
            throw new ReglasDeNegocioException("El DPI debe contener únicamente números. No se permiten letras ni caracteres especiales.");
        }
        if (dpi.length() != 13) {
            throw new ReglasDeNegocioException("El DPI debe contener exactamente 13 dígitos. Usted ingresó [" + dpi.length() + "] dígitos.");
        }
    }

    private void validarNit(String nit) {
        if (nit == null || nit.isBlank()) {
            throw new ReglasDeNegocioException("El campo NIT es obligatorio.");
        }
        if (!nit.matches("[a-zA-Z0-9]+")) { // Verifica que sea alfanumérico
            throw new ReglasDeNegocioException("El NIT debe contener únicamente caracteres alfanuméricos.");
        }
        if (nit.length() < 8 || nit.length() > 9) {
            throw new ReglasDeNegocioException("El NIT debe contener entre 8 y 9 caracteres. Usted ingresó [" + nit.length() + "] caracteres.");
        }
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return repo.findByActivo(true).stream()
                .map(usuarioMapper::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        return repo.findById(id).map(usuarioMapper::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorDpi(String dpi) {
        return repo.findByDpi(dpi).map(usuarioMapper::convertirADTO);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMINISTRADOR')") // SOLO LOS QUE SE LOGUEN COMO ADMINSITRADORES PUEDEN INVOCAR ESTE METODO
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {

        validarDpi(usuarioDTO.getDpi());
        validarNit(usuarioDTO.getNit());

        if (repo.existsByDpi(usuarioDTO.getDpi())) {
            throw new ReglasDeNegocioException("Ya existe un usuario con este numero de DPI" + usuarioDTO.getDpi());
        }
        if (repo.existsByEmail(usuarioDTO.getEmail())) {
            throw new ReglasDeNegocioException("Ya existe un usuario registrado con este Email");
        }

        Usuario nuevoUsuario = usuarioMapper.toEntity(usuarioDTO);
        nuevoUsuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        // si el adnimistrador envia un rol lo guarda si no por defecto le asigna el de pacinete
        nuevoUsuario.setRol(usuarioDTO.getRol() != null ? usuarioDTO.getRol() : UserRol.PACIENTE);
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setFechaCreacion(LocalDateTime.now());

        Usuario guardado = repo.save(nuevoUsuario);
        return usuarioMapper.convertirADTO(guardado);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioUpdateDTO dtoActualizado) {
        Usuario existe = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        if (dtoActualizado.getNombre() != null) {
            existe.setNombre(dtoActualizado.getNombre());
        }
        if (dtoActualizado.getApellidos() != null) {
            existe.setApellidos(dtoActualizado.getApellidos());
        }
        if (dtoActualizado.getEmail() != null) {
            existe.setEmail(dtoActualizado.getEmail());
        }
        if (dtoActualizado.getTelefono() != null) {
            existe.setTelefono(dtoActualizado.getTelefono());
        }
        if (dtoActualizado.getPassword() != null && !dtoActualizado.getPassword().isEmpty()) {
            existe.setPassword(passwordEncoder.encode(dtoActualizado.getPassword()));
        }
        if (dtoActualizado.getRol() != null) {
            existe.setRol(dtoActualizado.getRol());
        }
        if (dtoActualizado.getEsActivo() != null) {
            existe.setActivo(dtoActualizado.getEsActivo());
        }

        Usuario actualizado = repo.save(existe);
        return usuarioMapper.convertirADTO(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("usuario", "id", id));
        if(!usuario.getActivo()){
            throw new ReglasDeNegocioException("El usuario ya se encuentra inactivo o eliminado");
        }
        usuario.setActivo(false); // cambio de estado a falso(borrado logico)
        usuario.setFechaActualizacion(LocalDateTime.now()); // actualizacion de fecha de modificacion
        repo.save(usuario);

    }



}