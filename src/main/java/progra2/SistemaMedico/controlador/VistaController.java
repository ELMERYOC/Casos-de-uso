package progra2.SistemaMedico.controlador;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.request.LoginRequest;
import progra2.SistemaMedico.dto.request.RespuestaLogeo;
import progra2.SistemaMedico.excepciones.ReglasDeNegocioException;
import progra2.SistemaMedico.servicios.AutenticacionServicio;
import progra2.SistemaMedico.servicios.UsuarioService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VistaController {

    private final AutenticacionServicio autenticacionServicio;
    private final UsuarioService usuarioService;

    // --- CU-00: Portal Web ---
    @GetMapping("/")
    public String portal(Model model) {
        // Agregar información del portal al modelo
        model.addAttribute("servicios", obtenerServicios());
        model.addAttribute("especialidades", obtenerEspecialidades());
        model.addAttribute("ubicaciones", obtenerUbicaciones());
        model.addAttribute("horarios", obtenerHorariosAtencion());
        return "portal";
    }

    private List<String> obtenerServicios() {
        return List.of(
                "Consulta Externa",
                "Emergencias 24/7",
                "Laboratorio Clínico",
                "Radiología e Imágenes",
                "Farmacia",
                "Hospitalización"
        );
    }

    private List<String> obtenerEspecialidades() {
        return List.of(
                "Medicina General",
                "Pediatría",
                "Cardiología",
                "Ginecología",
                "Traumatología",
                "Dermatología",
                "Oftalmología",
                "Neurología"
        );
    }

    private List<String> obtenerUbicaciones() {
        return List.of(
                "Sucursal Central - Zona 10",
                "Sucursal Norte - Zona 16",
                "Sucursal Sur - Zona 8"
        );
    }

    private List<String> obtenerHorariosAtencion() {
        return List.of(
                "Lunes a Viernes: 8:00 AM - 6:00 PM",
                "Sábados: 8:00 AM - 12:00 PM",
                "Emergencias: 24 horas todos los días"
        );
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String error,
                               @RequestParam(required = false) String registro,
                               @RequestParam(required = false) String dpi, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Usuario o contraseña incorrectos. (Intentos restantes: verifique con admin)");
        }
        if (registro != null) {
            model.addAttribute("successMsg", "¡Registro exitoso! Por favor, inicie sesión.");
        }
        if (dpi != null) {
            model.addAttribute("prefillDpi", dpi);
        }
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(@RequestParam(required = false) String dpi, Model model) {
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        if (dpi != null) nuevoUsuario.setDpi(dpi); // Pre-llenar si viene del modal
        model.addAttribute("usuario", nuevoUsuario);
        return "registro";
    }
    @PostMapping("/procesar-login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            // 1. Llamamos a TU servicio existente (el que funcionaba en Postman)
            LoginRequest request = new LoginRequest(username, password);
            RespuestaLogeo respuesta = autenticacionServicio.login(request);

            // 2. Si llega aquí, el login fue exitoso. Guardamos datos en sesión.
            session.setAttribute("usuarioLogueado", respuesta);
            session.setAttribute("token", respuesta.getToken());

            // 3. Redirección según el rol
            if (Boolean.TRUE.equals(respuesta.getEsAdministrador())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/paciente/dashboard";
            }

        } catch (ReglasDeNegocioException e) {
            // 4. AQUÍ ESTÁ LA CLAVE: Capturamos la excepción que lanza tu servicio.
            // Tu servicio ya contiene el mensaje: "Tiene X intentos restantes..."
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/login"; // Regresamos al login para mostrar el error
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Ocurrió un error inesperado. Intente nuevamente.");
            return "redirect:/login";
        }
    }
    @PostMapping("/verificar-dpi")
    public String verificarDpi(@RequestParam String dpi, RedirectAttributes redirectAttributes) {
        var usuarioOpt = usuarioService.obtenerPorDpi(dpi);

        if (usuarioOpt.isPresent()) {
            var usuario = usuarioOpt.get();
            if (!usuario.getRol().name().equals("PACIENTE")) {
                redirectAttributes.addFlashAttribute("errorMsg", "Este DPI pertenece a personal interno. Por favor, use el panel administrativo.");
                return "redirect:/login";
            }
            return "redirect:/login?dpi=" + dpi;
        } else {
            // CORRECCIÓN: Agregar el mensaje flash antes de redirigir (CU-00 FA03)
            redirectAttributes.addFlashAttribute("errorMsg", "No se encontró un registro asociado a este DPI. Será redirigido al formulario de registro.");
            return "redirect:/registro?dpi=" + dpi;
        }
    }
    @PostMapping("/procesar-registro")
    public String procesarRegistro(@ModelAttribute UsuarioDTO dto, RedirectAttributes redirectAttributes) {
        try {
            autenticacionServicio.registrar(dto);
            return "redirect:/login?registro=exitoso";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage()); // FA02, FA03, FA04
            return "redirect:/registro";
        }
    }

    // --- CU-01: Mantenimiento de Usuarios (Admin) ---
    @GetMapping("/admin/usuarios")
    public String listarUsuarios(@RequestParam(required = false) String filtro,
                                 @RequestParam(required = false) String valor, Model model) {
        // Si hay filtro, podrías llamar a un método específico del servicio.
        // Por simplicidad, traemos todos y filtramos en vista, o implementas la búsqueda en el servicio.
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("filtro", filtro);
        model.addAttribute("valor", valor);
        return "admin/usuarios-lista";
    }

    @GetMapping("/admin/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        model.addAttribute("modo", "Crear");
        return "admin/usuarios-form";
    }
}