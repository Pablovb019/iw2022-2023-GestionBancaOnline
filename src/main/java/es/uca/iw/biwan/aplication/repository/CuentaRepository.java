package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO cuenta VALUES (:uuid, :iban, :balance)",
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

    @Modifying
    @Query(
            value = "DELETE usuario_cuentas FROM usuario_cuentas JOIN cuenta ON usuario_cuentas.cuentas_uuid = cuenta.uuid WHERE cuenta.uuid = :uuid",
            nativeQuery = true
    )
    void deleteCuenta(@Param("uuid") UUID uuid);

    @Query(
            value = "SELECT balance FROM cuenta WHERE uuid = :uuid",
            nativeQuery = true
    )
    String findTypeByUUID(@Param("uuid") UUID uuid);

    @Query(
            value = "SELECT * from cuenta INNER JOIN usuario_cuentas ON cuenta.iban = usuario_cuentas.cuentas_iban" +
                    " AND usuario_cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Cuenta> findCuentaByUUID(UUID uuid);

    @Query(
            value = "SELECT * from cuenta WHERE iban = :Iban",
            nativeQuery = true
    )
    Cuenta findCuentaByIban(String Iban);

    @Query(
            value = "SELECT * from cuenta INNER JOIN usuario_cuentas ON cuenta.uuid = usuario_cuentas.cuentas_uuid" +
                    " AND usuario_cuentas.clientes_uuid = :uuid",
            nativeQuery = true
    )
    ArrayList<Cuenta> findCuentaByCliente(UUID uuid);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Cuenta SET balance = :balance WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateBalance(@Param("uuid") UUID uuid,
                       @Param("balance") Double balance
    );

    @Query(
            value = "SELECT * from Cuenta JOIN Tarjeta ON Cuenta.uuid = Tarjeta.cuenta_id AND Tarjeta.numero_tarjeta = :numeroTarjeta",
            nativeQuery = true
    )
    Cuenta findCuentaByNumeroTarjeta(String numeroTarjeta);
}
