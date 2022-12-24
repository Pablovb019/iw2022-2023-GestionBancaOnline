package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta, String> {

}
