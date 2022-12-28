package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface TarjetaRepository extends JpaRepository<Tarjeta, String> {

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Tarjeta SET fecha_caducidad = :fecha_caducidad, activa = :activa, cvv = :cvv, limite_gasto = :limite_gasto WHERE numero_tarjeta = :numero_tarjeta",
            nativeQuery = true
    )
    void updateTarjeta(@Param("numero_tarjeta") String numero_tarjeta,
                    @Param("fecha_caducidad") LocalDate fecha_caducidad,
                    @Param("activa") Boolean activa,
                    @Param("cvv") String cvv,
                    @Param("limite_gasto") Float limite_gasto
    );

    @Query(
            value = "SELECT * from Tarjeta INNER JOIN Usuario_Cuentas ON Tarjeta.cuenta_id = Usuario_Cuentas.cuentas_iban" +
                    " AND Usuario_Cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Tarjeta> findTarjetaByUUID(UUID uuid);

    @Query(
            value = "SELECT tarjeta.cuenta_id FROM Tarjeta WHERE tarjeta.numero_tarjeta = :numero_tarjeta",
            nativeQuery = true
    )
    String findIbanByNumeroTarjeta(String numero_tarjeta);

}
