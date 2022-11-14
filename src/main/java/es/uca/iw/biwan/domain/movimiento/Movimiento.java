package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

public class Movimiento {

    protected float importe;
    protected LocalDateTime fecha;
    protected float balanceRestante;

    public Movimiento(float importe, LocalDateTime fecha, float balanceRestante) {
        this.importe = importe;
        this.fecha = fecha;
        this.balanceRestante = balanceRestante;
    }

    public float getImporte() {
        return importe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public float getBalanceRestante() {
        return balanceRestante;
    }

}

