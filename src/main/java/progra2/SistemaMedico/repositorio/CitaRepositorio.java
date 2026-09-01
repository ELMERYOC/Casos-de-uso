package progra2.SistemaMedico.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import progra2.SistemaMedico.model.Cita;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepositorio extends JpaRepository<Cita,Long> {

    // obtener todas las citas de un medico para calcular la disponibilidad
    @Query("SELECT c.fechaHora FROM Cita c WHERE c.medico.id = :medicoId AND DATE(c.fechaHora) = :fecha")
    List<LocalDateTime> findHorariosOcupados(@Param("medicoId") Long medicoId, @Param("fecha") LocalDate fecha);

    List<Cita> findByPacienteId(Long pacienteId);
}
