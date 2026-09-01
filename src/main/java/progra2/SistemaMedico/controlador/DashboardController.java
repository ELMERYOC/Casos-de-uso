package progra2.SistemaMedico.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.model.enums.UserRol;
import progra2.SistemaMedico.repositorio.UsuarioRepositorio;
import progra2.SistemaMedico.security.CustomUserDetailsService;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        // Obtener el usuario autenticado
        String username = authentication.getName();
        model.addAttribute("username", username);

        // Aquí deberías obtener el rol del usuario desde tu servicio
        // Por ahora, redirigimos según el rol que tengas en tu sistema
        return "dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // Esta vista debe existir en: src/main/resources/templates/admin/dashboard.html
        return "admin/dashboard";
    }

    @GetMapping("/paciente/dashboard")
    public String pacienteDashboard(Model model) {
        // Esta vista debe existir en: src/main/resources/templates/paciente/dashboard.html
        return "paciente/dashboard";
    }
}