package progra2.SistemaMedico.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import progra2.SistemaMedico.servicios.serviceImpl.IEmailService;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remitente;

    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombrePaciente) {
    try{
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setFrom(remitente);
        helper.setTo(destinatario);
        helper.setSubject("Bienvenido al sistema de citas - Hospital General");

            Context context = new Context();
            context.setVariable("nombre", nombrePaciente);
            String htmlContent = templateEngine.process("email-bienvenida", context);
            helper.setText(htmlContent, true);

        mailSender.send(mensaje);
        System.out.println("✅ Correo enviado exitosamente a: " + destinatario);

    } catch (MessagingException e) {
        // Log del error pero no rompemos el flujo de registro
        System.err.println("Error al enviar correo: " + e.getMessage());
        // Opcional: lanzar una excepción personalizada si el correo es crítico
        throw new RuntimeException("No se pudo enviar el correo de bienvenida", e);

    }

    }
}
