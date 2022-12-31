package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Movimiento VALUES (:uuid, :estado, :emisor, :tipo, :concepto, :iban, :valor, :cuenta)",
            nativeQuery = true
    )
    void saveMovimiento(@Param("uuid") UUID uuid,
                        @Param("estado") String estado,
                        @Param("emisor") String emisor,
                        @Param("tipo") String tipo,
                        @Param("concepto") String concepto,
                        @Param("iban") String iban,
                        @Param("valor") BigDecimal valor,
                        @Param("cuenta") UUID cuenta
    );
}
