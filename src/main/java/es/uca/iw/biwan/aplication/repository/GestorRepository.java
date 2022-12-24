package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GestorRepository extends JpaRepository<Gestor, UUID> {

}

