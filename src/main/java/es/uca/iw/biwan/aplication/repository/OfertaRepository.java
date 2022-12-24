package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfertaRepository extends JpaRepository<Oferta, UUID> {
}
