package progra2.SistemaMedico.controlador;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import progra2.SistemaMedico.dto.ApiResponse;
import progra2.SistemaMedico.dto.ApiResponseDTO;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.UsuarioUpdateDTO;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.repositorio.UsuarioRepositorio;
import progra2.SistemaMedico.servicios.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<ApiResponseDTO<List<UsuarioDTO>>> listarTodos(){
        List<UsuarioDTO> listarUsuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(ApiResponseDTO.success(listarUsuarios, "Lista usuarios exitosa"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"+ id));
        return ResponseEntity.ok(ApiResponseDTO.success(usuario, "Usuario encontrado"));
    }

    @PostMapping("/crear")
        public ResponseEntity<ApiResponseDTO<UsuarioDTO>> crearUsuarioPorAdmin(@Valid @RequestBody UsuarioDTO dto) throws Exception {
            UsuarioDTO usuario = usuarioService.crearUsuario(dto);
            return ResponseEntity.ok(ApiResponseDTO.success(usuario, "Usuario registrado exitosamente"));
        }
        @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO dtoActualizado){
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, dtoActualizado);
        return ResponseEntity.ok(ApiResponseDTO.success(usuarioActualizado, "Usuario actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);

        return ResponseEntity.ok(ApiResponseDTO.success(null, "Usuario eliminado correctaemente"));

    }

}
