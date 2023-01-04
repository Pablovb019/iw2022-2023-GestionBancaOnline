package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public interface PagoTarjetaRepository extends JpaRepository<PagoTarjeta, UUID> {

    @Transactional
    @Modifying
    @Query(
           value = "INSERT INTO Pago_Tarjeta VALUES (:uuid, :estado, :valor, :tipoPago, :tienda, :tarjeta)",
           nativeQuery = true
    )

    void savePagoTarjeta(@Param("uuid") UUID uuid,
                         @Param("estado") String estado,
                         @Param("valor") Double valor,
                         @Param("tipoPago") String tipoPago,
                         @Param("tienda") String tienda,
                         @Param("tarjeta") UUID tarjeta
    );

    @Query(
            value = "SELECT * FROM pago_tarjeta WHERE tarjeta_id = :tarjeta",
            nativeQuery = true
    )
    ArrayList<PagoTarjeta> findPagosTarjeta(
            @Param("tarjeta") UUID tarjeta
    );
}

