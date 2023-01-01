package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query(
            value = "SELECT rol FROM usuario WHERE uuid = :uuid",
            nativeQuery = true
    )
    String findRoleByUUID(@Param("uuid") UUID uuid);

    @Query(
            value = "SELECT * FROM usuario WHERE email = :email",
            nativeQuery = true
    )
    Usuario findUserByEmail(@Param("email") String email);

    @Query(
            value = "SELECT * FROM usuario WHERE telefono = :telefono",
            nativeQuery = true
    )
    Usuario findUserByTelefono(@Param("telefono") Double telefono);

    @Query(
            value = "SELECT * FROM usuario WHERE dni = :dni",
            nativeQuery = true
    )
    Usuario findUserByDni(@Param("dni") String dni);

    @Query(
            value = "SELECT * from usuario WHERE rol = :rol",
            nativeQuery = true
    )
    ArrayList<Usuario> findUsuarioByRol(@Param("rol") String rol);

    // CLIENTE

    @Modifying
    @Query(
            value = "INSERT INTO usuario VALUES (:role, :uuid, :nombre, :apellidos, :fechaNacimiento, :telefono, :dni, :email, :password, :gestor_id)",
            nativeQuery = true
    )
    void insertCliente(@Param("uuid") UUID uuid,
                    @Param("nombre") String nombre,
                    @Param("apellidos") String apellidos,
                    @Param("fechaNacimiento") LocalDate fechaNacimiento,
                    @Param("telefono") Double telefono,
                    @Param("dni") String dni,
                    @Param("email") String email,
                    @Param("role") String role,
                    @Param("password") String password,
                    @Param("gestor_id") UUID gestor_id
    );

    @Modifying
    @Query(
            value = "UPDATE usuario SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, telefono = :telefono, dni = :dni, email = :email, password = :password WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateCliente(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("password") String password
    );

    @Query(
            value = "SELECT * from usuario INNER JOIN usuario_cuentas ON usuario.uuid = usuario_cuentas.clientes_uuid" +
                    " AND usuario_cuentas.cuentas_uuid = :uuid AND usuario.rol = 'Cliente'",
            nativeQuery = true
    )
    Cliente findClienteByCuenta(@Param("uuid") UUID uuid);

    // GESTOR

    @Modifying
    @Query(
            value = "INSERT INTO usuario VALUES (:role, :uuid, :nombre, :apellidos, :fechaNacimiento, :telefono, :dni, :email, :password, NULL)",
            nativeQuery = true
    )
    void insertGestor(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("role") String role,
                       @Param("password") String password
    );

    @Modifying
    @Query(
            value = "UPDATE usuario SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, telefono = :telefono, dni = :dni, email = :email, password = :password WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateGestor(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("password") String password
    );

    // ENCARGADO COMUNICACIONES

    @Modifying
    @Query(
            value = "INSERT INTO usuario VALUES (:role, :uuid, :nombre, :apellidos, :fechaNacimiento, :telefono, :dni, :email, :password, NULL)",
            nativeQuery = true
    )
    void insertEncargado(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("role") String role,
                       @Param("password") String password
    );

    @Modifying
    @Query(
            value = "UPDATE usuario SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, telefono = :telefono, dni = :dni, email = :email, password = :password WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateEncargado(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("password") String password
    );

    // ADMINISTRADOR

    @Modifying
    @Query(
            value = "UPDATE usuario SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, telefono = :telefono, dni = :dni, email = :email, password = :password WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateAdministrador(@Param("uuid") UUID uuid,
                       @Param("nombre") String nombre,
                       @Param("apellidos") String apellidos,
                       @Param("fechaNacimiento") LocalDate fechaNacimiento,
                       @Param("telefono") Double telefono,
                       @Param("dni") String dni,
                       @Param("email") String email,
                       @Param("password") String password
    );
}
