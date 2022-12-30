package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Cuenta VALUES (:iban, :balance)",
            nativeQuery = true
    )
    void insertCuenta(@Param("iban") String iban,
                    @Param("balance") Double balance
    );

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO usuario_cuentas VALUES (:uuid, :iban)",
            nativeQuery = true
    )
    void relacionarCuenta(@Param("iban") String iban,
                          @Param("uuid") UUID uuid
    );

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

    @Query(
            value = "SELECT * from Cuenta WHERE iban = :Iban",
            nativeQuery = true
    )
    Cuenta findCuentaByIban(String Iban);

    @Query(
            value = "SELECT * from Cuenta INNER JOIN Usuario_Cuentas ON Cuenta.iban = Usuario_Cuentas.cuentas_iban" +
                    " AND Usuario_Cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Cuenta> findCuentaByCliente(UUID uuid);
}
