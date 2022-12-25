package es.uca.iw.biwan.domain.operaciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("PAGO_TARJETA")
public class PagoTarjeta extends Movimiento {


    private String informacion;
    private String establecimiento;

    public PagoTarjeta(float importe, LocalDateTime fecha, float balanceRestante, String informacion, String establecimiento) throws ImporteInvalidoException, FechaInvalidaException, BalanceRestanteInvalidoException {
        super(importe, fecha, balanceRestante);
        this.informacion = informacion;
        this.establecimiento = establecimiento;
    }

    public PagoTarjeta() {

    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }
    
}
