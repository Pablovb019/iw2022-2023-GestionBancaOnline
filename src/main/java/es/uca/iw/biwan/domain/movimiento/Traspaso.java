package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Traspaso extends Movimiento {
    
    private String cuentaOrigen;
    private String cuentaDestino;
    private String concepto;

    public Traspaso(float importe, LocalDateTime fecha, float balanceRestante, String cuentaOrigen, String cuentaDestino, String concepto) throws IllegalArgumentException {
        super(importe, fecha, balanceRestante);

        if(cuentaOrigen.length() != 24) {
            JSONObject json = new JSONObject();
            json.put("message", "La cuenta origen debe ser un IBAN valido");
            json.put("field", "cuentaOrigen");
            throw new IllegalArgumentException(json.toString());
        }

        if(cuentaDestino.length() != 24) {
            JSONObject json = new JSONObject();
            json.put("message", "La cuenta destino debe ser un IBAN valido");
            json.put("field", "cuentaDestino");
            throw new IllegalArgumentException(json.toString());
        }

        if(cuentaOrigen.equals(cuentaDestino)) {
            JSONObject json = new JSONObject();
            json.put("message", "La cuenta origen y la cuenta destino no pueden ser iguales");
            json.put("field", "cuentaDestino");
            throw new IllegalArgumentException(json.toString());
        }

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
