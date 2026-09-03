
package progra2.SistemaMedico.controlador;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import progra2.SistemaMedico.dto.ApiResponseDTO;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.request.LoginRequest;
import progra2.SistemaMedico.dto.request.RespuestaLogeo;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.servicios.AutenticacionServicio;

import javax.swing.plaf.PanelUI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {
    private final AutenticacionServicio autenticacion;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<RespuestaLogeo>> login(@Valid @RequestBody LoginRequest solicitudLogueo) {
        System.out.println("Controller login peticion recibida para el usuario:" + solicitudLogueo.getUsername());
        RespuestaLogeo respuesta = autenticacion.login(solicitudLogueo);
        System.out.println("logeo exitoso, se genera respuesta");

        return ResponseEntity.ok(ApiResponseDTO.<RespuestaLogeo>builder()
                .success(true)
                .message("Autentiacion exitosa")
                .data(respuesta)
                .build());
    }

    /**
     * Endpoint para registrar un nuevo usuario externo (Paciente).
     * POST /api/auth/register
     */
    @PostMapping("/registrar")
    public ResponseEntity<ApiResponseDTO<RespuestaLogeo>> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        RespuestaLogeo respuesta = autenticacion.registrar(usuarioDTO);

        return ResponseEntity.ok(ApiResponseDTO.<RespuestaLogeo>builder()
                .success(true)
                .message("Usuario registrado con exito")
                .data(respuesta)
                .build());

    }

    /**
     * Endpoint web para login con formulario HTML
     * Maneja intentos fallidos y muestra mensajes apropiados
     */
    @PostMapping("/login-web")
    public String procesarLoginWeb(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // 1. Verificar si el usuario existe para obtener intentos restantes
            Usuario usuarioExistente = autenticacion.obtenerPorUsername(username);

            // Verificar si está bloqueado
            if (usuarioExistente != null && usuarioExistente.getFechaBloqueo() != null &&
                    java.time.LocalDateTime.now().isBefore(usuarioExistente.getFechaBloqueo())) {
                long minutosRestantes = java.time.temporal.ChronoUnit.MINUTES.between(
                        java.time.LocalDateTime.now(), usuarioExistente.getFechaBloqueo());
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Cuenta bloqueada temporalmente. Intente en " + minutosRestantes + " minutos.");
                return "redirect:/login";
            }

            // 2. Autenticar con Spring Security
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Si es exitoso, los intentos se reinician automáticamente en el servicio

            // 4. Redirigir al primer paso de la cita
            return "redirect:/citas/paso/1";

        } catch (BadCredentialsException e) {
            // Obtener intentos restantes después del fallo
            Usuario usuarioFallido = autenticacion.obtenerPorUsername(username);
            int intentosRestantes = 5;
            if (usuarioFallido != null && usuarioFallido.getIntentosFallidos() != null) {
                intentosRestantes = 5 - usuarioFallido.getIntentosFallidos();
            }

            if (intentosRestantes <= 0) {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Cuenta bloqueada por múltiples intentos fallidos. Contacte al administrador o espere 15 minutos.");
            } else {
                redirectAttributes.addFlashAttribute("errorMsg",
                        "Usuario o contraseña incorrectos. Le quedan " + intentosRestantes + " intento(s) restante(s).");
            }
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/login";
        }
    }
}
