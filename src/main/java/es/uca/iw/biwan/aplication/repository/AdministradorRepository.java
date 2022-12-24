package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {

}
