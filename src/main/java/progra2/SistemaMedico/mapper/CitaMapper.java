package progra2.SistemaMedico.mapper;

import org.springframework.stereotype.Component;
import progra2.SistemaMedico.dto.CitaDTO;
import progra2.SistemaMedico.model.Cita;

@Component
public class CitaMapper {

    public CitaDTO convertirADTO(Cita cita){
    if (cita == null) return null;

    return CitaDTO.builder()
            .id(cita.getId())
            .numeroCita(cita.getNumeroCita())
            .nombreSucursal(cita.getSucursal().getNombre())
            .nombreEspecialidad(cita.getEspecialidad().getNombre())
            // obtenemos el nombre y apellido del medico
            .nombreMedico(cita.getMedico().getNombre() + ""+ cita.getMedico().getApellidos())
            .fechaHora(cita.getFechaHora())
            .motivo(cita.getMotivoConsulta())
            .estado(cita.getEstado().name())// conversion del enum a String con el .name
            .build();

    }

}
