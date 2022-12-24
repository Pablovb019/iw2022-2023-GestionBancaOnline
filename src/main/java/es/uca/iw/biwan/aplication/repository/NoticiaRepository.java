package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoticiaRepository extends JpaRepository<Noticia, UUID> {

}
