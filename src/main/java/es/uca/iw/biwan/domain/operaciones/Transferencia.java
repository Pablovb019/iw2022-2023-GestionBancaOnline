package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDate;

import org.json.JSONObject;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("TRANSFERENCIA")
public class Transferencia extends Movimiento {

    private String cuentaOrigen;
    private String cuentaDestino;
    private String beneficiario;
    private String concepto;

    public Transferencia(float importe, LocalDate fecha, float balanceRestante, String cuentaOrigen, String cuentaDestino, String beneficiario, String concepto) throws IllegalArgumentException {
        super(importe, fecha, balanceRestante);

        // TODO: La validation de los datos, y la construccion del JSON de la transferencia se ha de hacer en la vista, NUNCA en la clase
        //  Lo mismo para el resto de clases que usan JSON

        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.beneficiario = beneficiario;
        this.concepto = concepto;
    }

    public Transferencia() {

    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
