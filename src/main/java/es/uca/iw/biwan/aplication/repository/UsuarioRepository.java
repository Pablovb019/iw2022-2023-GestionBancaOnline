package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
