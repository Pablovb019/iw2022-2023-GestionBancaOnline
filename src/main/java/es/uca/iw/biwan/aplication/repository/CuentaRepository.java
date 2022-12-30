package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
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
            value = "INSERT INTO Cuenta VALUES (:uuid, :iban, :balance)",
            nativeQuery = true
    )
    void insertCuenta(@Param("uuid") UUID uuid,
                      @Param("iban") String iban,
                      @Param("balance") Double balance
    );

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO usuario_cuentas VALUES (:uuid_cliente, :uuid_cuenta)",
            nativeQuery = true
    )
    void relacionarCuenta(@Param("uuid_cuenta") UUID uuid_cuenta,
                          @Param("uuid_cliente") UUID uuid_cliente
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
            value = "SELECT * from Cuenta INNER JOIN Usuario_Cuentas ON Cuenta.uuid = Usuario_Cuentas.cuentas_uuid" +
                    " AND Usuario_Cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Cuenta> findCuentaByCliente(UUID uuid);

    @Query(
            value = "SELECT *  FROM Cliente INNER JOIN Usuario_Cuentas ON Cliente.uuid = Usuario_Cuentas.clientes_uuid" +
                    " AND Usuario_Cuentas.cuentas_iban = :iban",
            nativeQuery = true
    )
    Cliente findClienteByCuenta(String iban);

}
