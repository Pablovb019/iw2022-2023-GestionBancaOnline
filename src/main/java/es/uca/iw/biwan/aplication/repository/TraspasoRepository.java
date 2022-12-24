package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Traspaso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TraspasoRepository extends JpaRepository<Traspaso, UUID> {

}
