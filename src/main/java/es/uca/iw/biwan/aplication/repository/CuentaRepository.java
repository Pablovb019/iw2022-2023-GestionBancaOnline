package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.UUID;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    @Query(
            value = "SELECT balance FROM Cuenta WHERE uuid = :uuid",
            nativeQuery = true
    )
    String findTypeByUUID(@Param("uuid") UUID uuid);

    @Query(
            value = "SELECT * from Cuenta INNER JOIN Usuario_Cuentas ON Cuenta.iban = Usuario_Cuentas.cuentas_iban" +
                    " AND Usuario_Cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Cuenta> findCuentaByUUID(UUID uuid);
}
