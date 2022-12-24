package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.EncargadoComunicaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EncargadoComunicacionesRepository extends JpaRepository<EncargadoComunicaciones, UUID> {

}
