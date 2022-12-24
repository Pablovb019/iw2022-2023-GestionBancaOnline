package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDate;
import org.json.JSONObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("TRASPASO")
public class Traspaso extends Movimiento {
    
    private String cuentaOrigen;
    private String cuentaDestinatario;
    private String concepto;

    public Traspaso(float importe, LocalDate fecha, float balanceRestante, String cuentaOrigen, String cuentaDestinatario, String concepto) {
        super(importe, fecha, balanceRestante);

        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestinatario = cuentaDestinatario;
        this.concepto = concepto;
    }

    public Traspaso() {

    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestinatario() {
        return cuentaDestinatario;
    }

    public void setCuentaDestinatario(String cuentaDestinatario) {
        this.cuentaDestinatario = cuentaDestinatario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
