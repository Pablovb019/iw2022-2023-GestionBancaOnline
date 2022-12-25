package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {
    
    @Query("select t from Transferencia t where t.cuentaOrigen = :cuentaIban or t.cuentaDestino = :cuentaIban")
    List<Transferencia> search(@Param("cuentaIban") String cuentaIban);

}
