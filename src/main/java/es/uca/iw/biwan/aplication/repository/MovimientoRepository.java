package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

}
