package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {

}
