package progra2.SistemaMedico.controlador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import progra2.SistemaMedico.dto.ApiResponse;
import progra2.SistemaMedico.dto.CitaDTO;
import progra2.SistemaMedico.dto.EspecialidadDTO;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.servicios.serviceImpl.ICitaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaController {

    private final ICitaService citaService;

    // --- PASO 1: SUCURSAL ---
    @GetMapping("/paso/1")
    public String paso1Sucursal(Model model, HttpSession session) {
        session.removeAttribute("sucursalId");
        session.removeAttribute("especialidadId");
        session.removeAttribute("medicoId");
        session.removeAttribute("fechaHora"); // CORREGIDO: era "fechaHora.html"

        model.addAttribute("sucursales", citaService.obtenerSucursalesActivas());
        return "citas/paso-1";
    }

    @PostMapping("/paso/1")
    public String procesarPaso1(@RequestParam Long sucursalId, HttpSession session, RedirectAttributes redirectAttributes) {
        if (sucursalId == null) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una sucursal");
            return "redirect:/citas/paso/1";
        }
        session.setAttribute("sucursalId", sucursalId);
        return "redirect:/citas/paso/2";
    }

    // --- PASO 2: ESPECIALIDAD ---
    @GetMapping("/paso/2")
    public String paso2Especialidad(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long sucursalId = (Long) session.getAttribute("sucursalId");
        if (sucursalId == null) return "redirect:/citas/paso/1";

        List<EspecialidadDTO> especialidades = citaService.obtenerEspecialidadesPorSucursal(sucursalId);
        if (especialidades.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No hay especialidades disponibles para esta sucursal (FA01). Seleccione otra.");
            return "redirect:/citas/paso/1";
        }
        model.addAttribute("especialidades", especialidades);
        return "citas/paso-2"; // CORREGIDO: era redirect, ahora renderiza la vista con el modelo
    }

    @PostMapping("/paso/2")
    public String procesarPaso2(@RequestParam Long especialidadId, HttpSession session) {
        session.setAttribute("especialidadId", especialidadId);
        return "redirect:/citas/paso/3";
    }

    // --- PASO 3: MÉDICO ---
    @GetMapping("/paso/3")
    public String paso3Medico(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long sucursalId = (Long) session.getAttribute("sucursalId");
        Long especialidadId = (Long) session.getAttribute("especialidadId"); // CORREGIDO: typo

        if (sucursalId == null || especialidadId == null) return "redirect:/citas/paso/1";

        List<UsuarioDTO> medicos = citaService.obtenerMedicosDisponibles(sucursalId, especialidadId);
        if (medicos.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No se encontraron médicos disponibles para esta combinación (FA02).");
            return "redirect:/citas/paso/2";
        }
        model.addAttribute("medicos", medicos);
        return "citas/paso-3";
    }

    @PostMapping("/paso/3")
    public String procesarPaso3(@RequestParam Long medicoId, HttpSession session) {
        session.setAttribute("medicoId", medicoId);
        return "redirect:/citas/paso/4";
    }

    // --- PASO 4: FECHA Y HORA ---
    @GetMapping("/paso/4")
    public String paso4FechaHora(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Long medicoId = (Long) session.getAttribute("medicoId");
        if (medicoId == null) return "redirect:/citas/paso/1";

        LocalDate fecha = LocalDate.now().plusDays(1);
        List<String> horarios = citaService.obtenerHorariosDisponibles(medicoId, fecha);

        if (horarios.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No hay horarios disponibles para este médico en la fecha seleccionada (FA02).");
            return "redirect:/citas/paso/3";
        }
        model.addAttribute("fecha", fecha);
        model.addAttribute("horarios", horarios);
        return "citas/paso-4";
    }

    @PostMapping("/paso/4")
    public String procesarPaso4(@RequestParam LocalDate fecha, @RequestParam String hora, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            LocalDateTime fechaHora = LocalDateTime.of(fecha, LocalTime.parse(hora));
            session.setAttribute("fechaHora", fechaHora);
            return "redirect:/citas/paso/5";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Fecha u Hora inválida");
            return "redirect:/citas/paso/4";
        }
    }

    // --- PASO 5: CONFIRMACIÓN ---
    @GetMapping("/paso/5")
    public String paso5Confirmacion(HttpSession session, Model model) {
        Long sucursalId = (Long) session.getAttribute("sucursalId");
        Long especialidadId = (Long) session.getAttribute("especialidadId");
        Long medicoId = (Long) session.getAttribute("medicoId");
        LocalDateTime fechaHora = (LocalDateTime) session.getAttribute("fechaHora");

        if (sucursalId == null || especialidadId == null || medicoId == null || fechaHora == null) {
            return "redirect:/citas/paso/1";
        }

        // Obtener nombres para el resumen
        String sucursalNombre = citaService.obtenerSucursalesActivas().stream()
                .filter(s -> s.getId().equals(sucursalId)).map(s -> s.getNombre()).findFirst().orElse("Desconocida");
        String especialidadNombre = citaService.obtenerEspecialidadesPorSucursal(sucursalId).stream()
                .filter(e -> e.getId().equals(especialidadId)).map(e -> e.getNombre()).findFirst().orElse("Desconocida");
        String medicoNombre = citaService.obtenerMedicosDisponibles(sucursalId, especialidadId).stream()
                .filter(m -> m.getId().equals(medicoId)).map(m -> m.getNombre() + " " + m.getApellidos()).findFirst().orElse("Desconocido");

        model.addAttribute("sucursalNombre", sucursalNombre);
        model.addAttribute("especialidadNombre", especialidadNombre);
        model.addAttribute("medicoNombre", medicoNombre);
        model.addAttribute("fechaHora", fechaHora);

        return "citas/paso-5";
    }

    @PostMapping("/paso/5")
    public String procesarPaso5(@RequestParam String motivoConsulta, HttpSession session, RedirectAttributes redirectAttributes) {
        if (motivoConsulta == null || motivoConsulta.trim().length() < 10) {
            redirectAttributes.addFlashAttribute("error", "El motivo debe tener al menos 10 caracteres (RN-CU03-05).");
            return "redirect:/citas/paso/5";
        }

        ApiResponse.SolicitudCitaDTO solicitud = new ApiResponse.SolicitudCitaDTO();
        solicitud.setSucursalId((Long) session.getAttribute("sucursalId"));
        solicitud.setEspecialidadId((Long) session.getAttribute("especialidadId"));
        solicitud.setMedicoId((Long) session.getAttribute("medicoId"));
        solicitud.setFechaHora((LocalDateTime) session.getAttribute("fechaHora"));
        solicitud.setMotivoCita(motivoConsulta);

        try {
            CitaDTO citaCreada = citaService.agendarCita(solicitud);

            // Limpiar sesión CORREGIDO
            session.removeAttribute("sucursalId");
            session.removeAttribute("especialidadId");
            session.removeAttribute("medicoId");
            session.removeAttribute("fechaHora"); // CORREGIDO: era "fechaHora.html"

            redirectAttributes.addFlashAttribute("success", "Cita registrada exitosamente. Redirigiendo a pago...");
            return "redirect:/pago/" + citaCreada.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al agendar: " + e.getMessage());
            return "redirect:/citas/paso/5";
        }
    }

    // --- VOLVER ATRÁS ---
    @GetMapping("/volver/{pasoAnterior}")
    public String volver(@PathVariable int pasoAnterior, HttpSession session) {
        if (pasoAnterior == 1) {
            session.removeAttribute("sucursalId");
            session.removeAttribute("especialidadId");
            session.removeAttribute("medicoId");
            session.removeAttribute("fechaHora"); // CORREGIDO
        } else if (pasoAnterior == 2) {
            session.removeAttribute("especialidadId");
            session.removeAttribute("medicoId");
            session.removeAttribute("fechaHora"); // CORREGIDO
        } else if (pasoAnterior == 3) {
            session.removeAttribute("medicoId");
            session.removeAttribute("fechaHora"); // CORREGIDO
        } else if (pasoAnterior == 4) {
            session.removeAttribute("fechaHora"); // CORREGIDO
        }
        return "redirect:/citas/paso/" + pasoAnterior;
    }
}

