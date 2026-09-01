package progra2.SistemaMedico.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import progra2.SistemaMedico.model.Especialidad;
import progra2.SistemaMedico.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio  extends JpaRepository<Usuario, Long> {
    /*interface que acuta como el acceso a los datos*/
    Optional<Usuario> findByUsername(String username) ;
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDpi(String  dip);
    Optional<Usuario> findByNit(String nit);

    boolean existsByUsername (String username);
    boolean existsByEmail (String email);
    boolean existsByDpi(String dpi);
    boolean existsByNit(String nit);

    List<Usuario> findByActivo(boolean activo);

    // RELACIONES CON ESPECIALIDAD, SUCURSAL Y MEDICOS
    // 1. Obtiene las especialidades que tienen al menos un médico activo en esa sucursal
    @Query("SELECT DISTINCT u.especialidad FROM Usuario u WHERE u.sucursal.id = :sucursalId AND u.rol = 'MEDICO' AND u.activo = true AND u.especialidad.activo = true")
    List<Especialidad> findEspecialidadesDisponiblesPorSucursal(@Param("sucursalId") Long sucursalId);

    // 2. Obtiene los médicos de una sucursal y especialidad específica
    @Query("SELECT u FROM Usuario u WHERE u.sucursal.id = :sucursalId AND u.especialidad.id = :especialidadId AND u.rol = 'MEDICO' AND u.activo = true")
    List<Usuario> findMedicoBySucursalYEspecialidad(@Param("sucursalId") Long sucursalId, @Param("especialidadId") Long especialidadId);

}
