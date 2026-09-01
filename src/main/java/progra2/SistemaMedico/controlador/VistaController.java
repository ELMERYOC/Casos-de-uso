package progra2.SistemaMedico.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.servicios.AutenticacionServicio;
import progra2.SistemaMedico.servicios.UsuarioService;

@Controller
@RequiredArgsConstructor
public class VistaController {

    private final AutenticacionServicio autenticacionServicio;
    private final UsuarioService usuarioService;

    // --- CU-00: Portal Web ---
    @GetMapping("/")
    public String portal() {
        return "portal";
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

    @PostMapping("/verificar-dpi")
    public String verificarDpi(@RequestParam String dpi, RedirectAttributes redirectAttributes) {
        // El servicio ya se encarga de validar o puedes confiar en la validación HTML5 del formulario
        var usuarioOpt = usuarioService.obtenerPorDpi(dpi);

        if (usuarioOpt.isPresent()) {
            var usuario = usuarioOpt.get();
            // FA04: Si es personal interno, no debe agendar por el portal
            if (!usuario.getRol().name().equals("PACIENTE")) {
                redirectAttributes.addFlashAttribute("errorMsg", "Este DPI pertenece a personal interno. Por favor, use el panel administrativo.");
                return "redirect:/login";
            }
            // Flujo normal: Es paciente, enviarlo al login con el DPI pre-llenado
            return "redirect:/login?dpi=" + dpi;
        } else {
            // FA03: No está registrado, enviarlo al registro con el DPI pre-llenado
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