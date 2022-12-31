package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.nio.DoubleBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, String> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Tarjeta VALUES (:uuid, :numero_tarjeta, :fecha_caducidad, :cvv, :pin, :limite_gasto, :activa, :cuenta_id)",
            nativeQuery = true
    )
    void insertTarjeta(@Param("uuid") UUID uuid,
                       @Param("numero_tarjeta") String numero_tarjeta,
                       @Param("fecha_caducidad") LocalDate fecha_caducidad,
                       @Param("activa") Boolean activa,
                       @Param("cvv") String cvv,
                       @Param("pin") Integer pin,
                       @Param("limite_gasto") Double limite_gasto,
                       @Param("cuenta_id") UUID cuenta_id
    );

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Tarjeta SET fecha_caducidad = :fecha_caducidad, activa = :activa, cvv = :cvv, pin = :pin, limite_gasto = :limite_gasto WHERE numero_tarjeta = :numero_tarjeta",
            nativeQuery = true
    )
    void updateTarjeta(@Param("numero_tarjeta") String numero_tarjeta,
                    @Param("fecha_caducidad") LocalDate fecha_caducidad,
                    @Param("activa") Boolean activa,
                    @Param("cvv") String cvv,
                    @Param("pin") Integer pin,
                    @Param("limite_gasto") Double limite_gasto
    );

    @Modifying
    @Query(
            value = "DELETE FROM Tarjeta WHERE Tarjeta.uuid = :uuid",
            nativeQuery = true
    )
    void deleteTarjeta(@Param("uuid") UUID uuid);

    @Query(
            value = "SELECT * from Tarjeta INNER JOIN Usuario_Cuentas ON Tarjeta.cuenta_id = Usuario_Cuentas.cuentas_uuid" +
                    " AND Usuario_Cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Tarjeta> findTarjetaByUUID(UUID uuid);

    @Query(
            value = "SELECT Cuenta.iban FROM Cuenta INNER JOIN Tarjeta ON Cuenta.uuid = Tarjeta.cuenta_id " +
                    "AND Tarjeta.numero_tarjeta = :numero_tarjeta",
            nativeQuery = true
    )
    String findIbanByNumeroTarjeta(String numero_tarjeta);

    @Query(
            value = "SELECT * FROM Tarjeta WHERE Tarjeta.numero_tarjeta = :numeroTarjeta",
            nativeQuery = true
    )
    Tarjeta findTarjetaByNumeroTarjeta(String numeroTarjeta);

    @Query(
            value = "SELECT COUNT(uuid) FROM Tarjeta WHERE Tarjeta.cuenta_id = :uuid",
            nativeQuery = true
    )
    int findTarjetaByCuentaUUID(UUID uuid);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Tarjeta SET limite_gasto = :limite_gasto WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateLimiteGasto(@Param("limite_gasto") Double limiteGasto,
                           @Param("uuid") UUID uuid);
}
