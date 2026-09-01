package progra2.SistemaMedico.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import progra2.SistemaMedico.dto.*;
import progra2.SistemaMedico.dto.CitaDTO;
import progra2.SistemaMedico.excepciones.ReglasDeNegocioException;
import progra2.SistemaMedico.mapper.CitaMapper;
import progra2.SistemaMedico.model.Cita;
import progra2.SistemaMedico.model.Especialidad;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.model.enums.AppointmentStatus;
import progra2.SistemaMedico.repositorio.CitaRepositorio;
import progra2.SistemaMedico.repositorio.EspecialidadRepositorio;
import progra2.SistemaMedico.repositorio.SucursalRepositorio;
import progra2.SistemaMedico.repositorio.UsuarioRepositorio;
import progra2.SistemaMedico.servicios.serviceImpl.ICitaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService implements ICitaService {
    private final CitaRepositorio citaRepo;
    private final UsuarioRepositorio userRepo;
    private final SucursalRepositorio sucursalRepo;
    private final EspecialidadRepositorio especialidadRepo;
    private final CitaMapper mapper;

@Override
//@Cacheable(value = "sucursales")
    public List<SucursalDTO> obtenerSucursalesActivas() {
    System.out.println("Buscando sucursales en la Base de Datos");
    return sucursalRepo.findByActivoTrue().stream()
            .map(s -> new SucursalDTO(
                    s.getId(),
                    s.getNombre(),
                    s.getDireccion(),
                    s.getTelefono(),
                    s.getEmail(),
                    s.getActivo(),
                    s.getFechaCreacion(),
                    s.getFechaActualizacion()
                    ))
            .collect(Collectors.toList());
    }


    @Override
    //@Cacheable(value = "especialidades")
    public List<EspecialidadDTO> obtenerEspecialidadesPorSucursal(Long sucursalId) {
        System.out.println("Buscando especialidades para la sucursal ID: "+ sucursalId);
    List<Especialidad> especialidades = userRepo.findEspecialidadesDisponiblesPorSucursal(sucursalId);

    if(especialidades.isEmpty()){
        System.out.println("no se encontro especialidades activas para la siguiene sucursal: " + sucursalId );
    }
    return especialidades.stream()
            .map(e -> new EspecialidadDTO(
                    e.getId(),
                    e.getNombre(),
                    e.getDescripcion(),
                    e.getActivo(),
                    e.getFechaCreacion(),
                    e.getFechaActualizacion()

            ))
            .collect(Collectors.toList());
    }
    @Override
    public List<UsuarioDTO> obtenerMedicosDisponibles(Long sucursalId, Long especialidadId) {
        return userRepo.findMedicoBySucursalYEspecialidad(sucursalId, especialidadId)
                .stream()
                .map(U -> UsuarioDTO.builder()
                        .id(U.getId())
                        .rol(U.getRol())
                        .dpi(U.getDpi())
                        .nit(U.getNit())
                        .nombre(U.getNombre())
                        .apellidos(U.getApellidos())
                        .username(U.getUsername())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> obtenerHorariosDisponibles(Long medicoId, LocalDate fecha) {
    List<String> todosLosHorarios = new ArrayList<>();
    LocalTime horaInicio = LocalTime.of(8,0);
    LocalTime horaFin = LocalTime.of(16,0);

    while (horaInicio.isBefore(horaFin)){
        todosLosHorarios.add(horaInicio.toString());
        horaInicio = horaInicio.plusMinutes(30);
    }
    //horarios ocupados
        List<LocalDateTime> ocupados = citaRepo.findHorariosOcupados(medicoId, fecha);
    List<String> horariosOcupados = ocupados.stream()
            .map(o -> o.toLocalTime().toString())
            .collect(Collectors.toList()    );
// filtrar horarios libres
        todosLosHorarios.removeAll(horariosOcupados);
        return todosLosHorarios;
        }

    @Override
    public CitaDTO agendarCita(ApiResponse.SolicitudCitaDTO solicitudCitaDTO) {
        // obtener al paciente previamente autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario paciente = userRepo.findByUsername(username)
                .orElseThrow(() -> new ReglasDeNegocioException("Usuario autenticado no encontrado"));
// validacion de horario siga disponible
        boolean estaOcupado = citaRepo.findHorariosOcupados(solicitudCitaDTO.getMedicoId(), solicitudCitaDTO.getFechaHora().toLocalDate())
                .contains(solicitudCitaDTO.getFechaHora());

        if(estaOcupado){
            throw new RuntimeException("El horario seleccionado ha sido ocupado");
        }
        Cita nuevaCita = Cita.builder()
                .numeroCita("CITA: " + System.currentTimeMillis())
                .paciente(paciente)
                .medico(userRepo.findById(solicitudCitaDTO.getMedicoId()).orElseThrow())
                .sucursal(sucursalRepo.findById(solicitudCitaDTO.getSucursalId()).orElseThrow())
                .especialidad(especialidadRepo.findById(solicitudCitaDTO.getEspecialidadId()).orElseThrow())
                .fechaHora(solicitudCitaDTO.getFechaHora())
                .motivoConsulta(solicitudCitaDTO.getMotivoCita())
                .estado(AppointmentStatus.PENDIENTE_PAGO)
                .fechaReservaExpira(LocalDateTime.now().plusMinutes(5))
                .build();
        //guaradar la entiad en la base de datos
        Cita citaGuardada = citaRepo.save(nuevaCita);
        // convertir la entidad guardada a DTO para respusta al cliente
        return mapper.convertirADTO(citaGuardada);




}


    @Override
    public List<CitaDTO> obenerCitasPorPaciente(Long pacienteId) {
        return List.of();
    }
}
