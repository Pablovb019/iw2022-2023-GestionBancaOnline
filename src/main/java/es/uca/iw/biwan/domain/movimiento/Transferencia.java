package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

public class Transferencia extends Movimiento {

    private String cuentaOrigen;
    private String cuentaDestino;
    private String beneficiario;
    private String concepto;

    public Transferencia(float importe, LocalDateTime fecha, float balanceRestante, String cuentaOrigen, String cuentaDestino, String beneficiario, String concepto) {
        super(importe, fecha, balanceRestante);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.beneficiario = beneficiario;
        this.concepto = concepto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public String getConcepto() {
        return concepto;
    }
    
}
