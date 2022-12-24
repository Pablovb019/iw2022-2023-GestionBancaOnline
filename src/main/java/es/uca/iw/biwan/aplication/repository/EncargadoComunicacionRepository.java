package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.EncargadoComunicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EncargadoComunicacionRepository extends JpaRepository<EncargadoComunicacion, UUID> {

}
