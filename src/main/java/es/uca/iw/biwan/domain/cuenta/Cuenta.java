package es.uca.iw.biwan.domain.cuenta;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import org.apache.commons.lang3.RandomStringUtils;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Cuenta {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;

    @Column(nullable = false)
    private String IBAN;

    @Column(nullable = false)
    private double balance;

    @ManyToMany(mappedBy = "cuentas")
    private List<Cliente> clientes;

    @OneToMany
    @JoinColumn(name = "cuenta_id")
    private List<Tarjeta> tarjetas;

    @OneToMany()
    @JoinColumn(name = "cuenta_id")
    private List<Movimiento> movimientos;

    public Cuenta() {
        this.uuid = UUID.randomUUID();
        this.IBAN = Iban.random(CountryCode.ES).toString();
        this.balance = 0;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void IngresosAnuales() {

    }

    public void GastosAnuales() {

    }

    public void RealizarMovimiento() {

    }

    public void BuscarMovimiento() {

    }

    public void ConsultarTarjetas() {

    }
}
