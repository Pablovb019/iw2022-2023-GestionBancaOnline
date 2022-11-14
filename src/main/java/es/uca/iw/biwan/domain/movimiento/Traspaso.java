package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

public class Traspaso extends Movimiento {
    
    private String cuentaOrigen;
    private String cuentaDestino;
    private String concepto;

    public Traspaso(float importe, LocalDateTime fecha, float balanceRestante, String cuentaOrigen, String cuentaDestino, String concepto) {
        super(importe, fecha, balanceRestante);
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.concepto = concepto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public String getConcepto() {
        return concepto;
    }
    

}
