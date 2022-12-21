package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Persona;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Usuario VALUES (:uuid, :nombre, :apellidos, :fechaNacimiento, :telefono, :dni, :email, :password)",
            nativeQuery = true
    )
    void insertarUsuario(@Param("uuid") UUID uuid,
                         @Param("nombre") String nombre,
                         @Param("apellidos") String apellidos,
                         @Param("fechaNacimiento") LocalDate fechaNacimiento,
                         @Param("telefono") Double telefono,
                         @Param("dni") String dni,
                         @Param("email") String email,
                         @Param("password") String password
    );

    @Query("SELECT u FROM Persona u WHERE u.email = :email")
    public Persona getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM Persona u WHERE u.dni = :dni")
    public Persona getUserByDni(@Param("dni") String dni);

    Usuario getByUsername(String username);

    Usuario getByActivationCode(String activationCode);
}
