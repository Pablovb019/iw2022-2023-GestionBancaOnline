package es.uca.iw.biwan.domain.operaciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("TRANSFERENCIA")
public class Transferencia extends Movimiento {
    private String iban_destino;

    public String getIbanDestino() {
        return iban_destino;
    }

    public void setIbanDestino(String ibanDestino) {
        this.iban_destino = ibanDestino;
    }
}
