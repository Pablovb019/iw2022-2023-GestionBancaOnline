package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.operaciones.Estado;
import es.uca.iw.biwan.domain.operaciones.TransaccionBancaria;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO transferencia VALUES (:uuid, :estado, :emisor, :tipo, :concepto, :iban_origen, :valor, :cuenta, :iban_destino)",
            nativeQuery = true
    )
    void saveTransferencia(@Param("uuid") UUID id,
                            @Param("estado") String transactionStatus,
                            @Param("emisor") String issuer,
                            @Param("tipo") String transactionType,
                            @Param("concepto") String concept,
                            @Param("iban_origen") String iban,
                            @Param("valor") BigDecimal value,
                            @Param("cuenta") UUID cuenta,
                            @Param("iban_destino") String iban_destino);
}
