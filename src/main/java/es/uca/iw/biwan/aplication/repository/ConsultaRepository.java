package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

}
