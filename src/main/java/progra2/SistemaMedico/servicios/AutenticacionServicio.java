package progra2.SistemaMedico.servicios;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import progra2.SistemaMedico.dto.UsuarioDTO;
import progra2.SistemaMedico.dto.request.RespuestaLogeo;
import progra2.SistemaMedico.dto.request.LoginRequest;
import progra2.SistemaMedico.excepciones.ReglasDeNegocioException;
import progra2.SistemaMedico.mapper.UsuarioMapper;
import progra2.SistemaMedico.model.Usuario;
import progra2.SistemaMedico.model.enums.UserRol;
import progra2.SistemaMedico.repositorio.UsuarioRepositorio;
import progra2.SistemaMedico.security.GeneradorDeJwt;
import progra2.SistemaMedico.servicios.serviceImpl.IAutenticacionServicio;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import progra2.SistemaMedico.servicios.serviceImpl.IEmailService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service

public class AutenticacionServicio implements IAutenticacionServicio {

    @Autowired
    private AuthenticationManager authenticationManager; // interface de spring security para Valida si usuario/contraseña son correctos.
    @Autowired
    private GeneradorDeJwt jwtGenerado;

    @Autowired
    private UsuarioRepositorio repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private GestionIntentosFallidos gestionIntentosFallidos;

    @Autowired
    private IEmailService emailService;

    @Override
    @Transactional
    public RespuestaLogeo login(LoginRequest solicitudDeLogueo) {
        System.out.println(" SERVICIO: Usuario encontrado, procediendo a generar token...");


        Usuario usuario = repo.findByUsername(solicitudDeLogueo.getUsername())
                .orElseThrow(() -> new ReglasDeNegocioException("Las credenciales ingresadas son incorrectas"));

        // validcion de bloqueo por multiplesi intentos
        if (usuario.getFechaBloqueo() != null && usuario.getFechaBloqueo().isAfter(LocalDateTime.now())) {
            long minutosRestantes = ChronoUnit.MINUTES.between(LocalDateTime.now(), usuario.getFechaBloqueo());
            throw new ReglasDeNegocioException("Su cuenta ha sido bloqueadad temporalmente  por multimples intentos fallidos. Intente de nuevo en" + minutosRestantes + "minuros");
        }
        //validar si el usuario esta activo
        if (!usuario.getActivo()) {
            throw new ReglasDeNegocioException("El usaurio se encuentra inactivo contacte al administrador");
        }
        try {
            // 4. Intentar autenticar con Spring Security (valida la contraseña)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(solicitudDeLogueo.getUsername(), solicitudDeLogueo.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails details = (UserDetails) authentication.getPrincipal();

            // si el logueo es exitoso reinicia el conteo de errores
            usuario.setIntentosFallidos(0);
            usuario.setFechaBloqueo(null);
            usuario.setFechaActualizacion(LocalDateTime.now());
            repo.save(usuario);

            //generar token y devolver la respuesta
            String token = jwtGenerado.generateToken(details);
            return RespuestaLogeo.builder()
                    .token(token)
                    .tipoDeToken("Bearer")
                    .username(usuario.getUsername())
                    .usuarioId(usuario.getId())
                    .dpi(usuario.getDpi())
                    .nombre(usuario.getNombre())
                    .apellidos(usuario.getApellidos())
                    .esAdministrador(usuario.getRol() == UserRol.ADMINISTRADOR)
                    .rol(usuario.getRol())
                    .build();

        } catch (BadCredentialsException e) {
            gestionIntentosFallidos.resistrarYGuardarIntentosFallidos(usuario);
            Usuario usuarioActualizado = repo.findById(usuario.getId()).orElseThrow();
            int intentosRestantes = 5 - usuarioActualizado.getIntentosFallidos();

            if (usuarioActualizado.getIntentosFallidos() >= 5) {

                throw new ReglasDeNegocioException("Cuenta bloqueada temporalmente por múltiples intentos fallidos. Contacte al administrador del sistema o espere 15 minutos.");
            } else {
                throw new ReglasDeNegocioException("Las credenciales ingresadas son incorrectas. Tiene " + intentosRestantes + " intentos restantes antes del bloqueo temporal.");
            }
        }
    }


    @Override
    @Transactional
    public RespuestaLogeo registrar(UsuarioDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new ReglasDeNegocioException("El correo electronico ya esta registrado");
        }
        if (repo.existsByDpi(dto.getDpi())) {
            throw new ReglasDeNegocioException("El DPI ya esta registrado");
        }
        if (repo.existsByUsername(dto.getUsername())) {
            throw new ReglasDeNegocioException("El nombre de usuario ya esta registrado en la base de datos");
        }
        Usuario usuario = mapper.toEntity(dto);
        usuario.setPassword(encoder.encode(dto.getPassword()));
        usuario.setActivo(true);

        if (usuario.getRol() != null) {
            usuario.setRol(UserRol.PACIENTE);
        }

        Usuario usuarioGuaradado = repo.save(usuario);
        try {
            String nombreCompleto = usuarioGuaradado.getNombre() + " " + usuarioGuaradado.getApellidos();
            emailService.enviarCorreoBienvenida(usuarioGuaradado.getEmail(), nombreCompleto);
        } catch (Exception e) {
            // se lanza este log para no bloquear el registro del usuario en dado caso el servidor de correos falle
            System.err.println("El usuario fue registrado exitosamente, pero falló el envío del correo de bienvenida: " + e.getMessage());
        }


        Usuario usuarioGuardado = repo.save(usuario);
// SE GENERA TOKEN PARA INICIO DE SESION AUTOMATICO
        UserDetails details = org.springframework.security.core.userdetails.User
                .withUsername(usuarioGuardado.getUsername())
                .password(usuarioGuardado.getPassword())
                .authorities(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + usuarioGuardado.getRol().name()))
                .disabled(!usuarioGuardado.getActivo())
                .build();

        String token = jwtGenerado.generateToken(details);

        return RespuestaLogeo.builder()
                .token(token)
                .tipoDeToken("Bearer")
                .username(usuarioGuardado.getUsername())
                .usuarioId(usuarioGuardado.getId())
                .dpi(usuarioGuardado.getDpi())
                .nombre(usuarioGuardado.getNombre())
                .apellidos(usuarioGuardado.getApellidos())
                .esAdministrador(usuarioGuardado.getRol() == UserRol.ADMINISTRADOR)
                .rol(usuarioGuardado.getRol())
                .build();


    }

}
