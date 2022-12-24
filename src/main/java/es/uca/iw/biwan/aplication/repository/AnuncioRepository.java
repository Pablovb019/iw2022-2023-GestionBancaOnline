package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnuncioRepository extends JpaRepository<Anuncio, UUID> {

}
