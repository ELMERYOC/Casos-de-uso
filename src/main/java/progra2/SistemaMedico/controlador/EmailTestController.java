package progra2.SistemaMedico.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progra2.SistemaMedico.servicios.serviceImpl.IEmailService;

@RestController
@RequestMapping("/api/test")
public class EmailTestController {

    private final IEmailService emailService;

    public EmailTestController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/enviar-correo")
    public ResponseEntity<String> enviarCorreoPrueba() {
        try {
            emailService.enviarCorreoBienvenida("tu_correo_personal@gmail.com", "Juan Pérez");
            return ResponseEntity.ok("✅ Correo enviado exitosamente. Revisa tu bandeja de Mailtrap.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}