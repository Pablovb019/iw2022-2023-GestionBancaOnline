package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.ReciboDomiciliado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReciboDomiciliadoRepository extends JpaRepository<ReciboDomiciliado, UUID> {

}
