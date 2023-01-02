package es.uca.iw.biwan.domain.operaciones;

import es.uca.iw.biwan.domain.cuenta.Cuenta;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("TRASPASO")
public class Traspaso extends Movimiento {
    private String iban_destino;

    public String getIbanDestino() {
        return iban_destino;
    }

    public void setIbanDestino(String ibanDestino) {
        this.iban_destino = ibanDestino;
    }
}
