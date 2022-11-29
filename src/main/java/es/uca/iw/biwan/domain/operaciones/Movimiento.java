package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Movimiento {

    protected float importe;
    protected LocalDateTime fecha;
    protected float balanceRestante;

    public Movimiento(float importe, LocalDateTime fecha, float balanceRestante) throws IllegalArgumentException {
        if(importe == 0) {
            JSONObject json = new JSONObject();
            json.put("message", "El importe no puede ser 0");
            json.put("field", "importe");
            throw new IllegalArgumentException(json.toString());
        }
        if(fecha == null) {
            JSONObject json = new JSONObject();
            json.put("message", "La fecha no puede ser nula");
            json.put("field", "fecha");
            throw new IllegalArgumentException(json.toString());
        }
        if(balanceRestante < 0) {
            throw new IllegalArgumentException("Balance restante no puede ser negativo");
        }

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

