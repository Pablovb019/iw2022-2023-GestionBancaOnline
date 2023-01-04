package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

    @Modifying
    @Query(
            value = "INSERT INTO movimiento VALUES ('MOVIMIENTO', :uuid, :estado, :emisor, :tipo_movimiento, :concepto, :iban, :valor, NULL, :cuenta)",
            nativeQuery = true
    )
    void saveMovimiento(
                        @Param("uuid") UUID uuid,
                        @Param("estado") String estado,
                        @Param("emisor") String emisor,
                        @Param("tipo_movimiento") String tipo,
                        @Param("concepto") String concepto,
                        @Param("iban") String iban,
                        @Param("valor") BigDecimal valor,
                        @Param("cuenta") UUID cuenta
    );

    @Modifying
    @Query(
            value = "INSERT INTO movimiento VALUES ('TRANSFERENCIA', :uuid, :estado, :emisor, :tipo_movimiento, :concepto, :iban, :valor, :iban_destino, :cuenta)",
            nativeQuery = true
    )
    void saveTransferencia(
            @Param("uuid") UUID uuid,
            @Param("estado") String estado,
            @Param("emisor") String emisor,
            @Param("tipo_movimiento") String tipo,
            @Param("concepto") String concepto,
            @Param("iban") String iban,
            @Param("valor") BigDecimal valor,
            @Param("iban_destino") String ibanDestino,
            @Param("cuenta") UUID cuenta
    );

    @Modifying
    @Query(
            value = "INSERT INTO movimiento VALUES ('TRASPASO', :uuid, :estado, :emisor, :tipo_movimiento, :concepto, :iban, :valor, :iban_destino, :cuenta)",
            nativeQuery = true
    )
    void saveTraspaso(
            @Param("uuid") UUID uuid,
            @Param("estado") String estado,
            @Param("emisor") String emisor,
            @Param("tipo_movimiento") String tipo,
            @Param("concepto") String concepto,
            @Param("iban") String iban,
            @Param("valor") BigDecimal valor,
            @Param("iban_destino") String ibanDestino,
            @Param("cuenta") UUID cuenta
    );

    @Query(
            value = "SELECT * FROM movimiento WHERE tipo = 'MOVIMIENTO' AND cuenta_id = :cuenta",
            nativeQuery = true
    )

    ArrayList<Movimiento> findAllMovimientos(
            @Param("cuenta") UUID uuid
    );

    @Query(
            value = "SELECT * FROM movimiento WHERE tipo = 'TRANSFERENCIA' AND cuenta_id = :cuenta",
            nativeQuery = true
    )

    ArrayList<Transferencia> findAllTransferencias(
            @Param("cuenta") UUID uuid
    );

    @Query(
            value = "SELECT * FROM movimiento WHERE tipo = 'TRASPASO' AND cuenta_id = :cuenta",
            nativeQuery = true
    )

    ArrayList<Traspaso> findAllTraspasos(
            @Param("cuenta") UUID uuid
    );
}
