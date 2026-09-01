package progra2.SistemaMedico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import progra2.SistemaMedico.model.enums.UserRol;

import java.time.LocalDateTime;


@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, length = 13)
    private String dpi;

    @Column(length = 9)
    private String nit;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 8)
    private String telefono;

    @Column(length = 50)
    private String numeroSeguro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRol rol;

    @Column(nullable = false)
    private Boolean activo = true;

    //RELACIONES
    //MUCHOS A UNO, MUCHOS USUARIOS PUEDEN ESTAR EN UNA SUCURSAL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")//LLAVE FORANEA APUNTANDO AL ID DE LA TABLA SUCURSAL
    private Sucursal sucursal;

    //MUCHOS USUARIOS PUEDEN TENER UNA ESPECIALIDAD APUNTANDO AL ID DE LA TABLA ESPECIALIDAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")//LLAVE FORANEA
    private Especialidad especialidad;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    @Column(length = 50)
    private LocalDateTime ultimoIntentoFallido;

    @Column
    private Integer intentosFallidos = 0;

    @Column
    private LocalDateTime fechaBloqueo;
}


