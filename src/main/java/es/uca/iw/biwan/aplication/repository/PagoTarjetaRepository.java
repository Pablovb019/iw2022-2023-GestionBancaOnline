package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagoTarjetaRepository extends JpaRepository<PagoTarjeta, UUID> {

}

