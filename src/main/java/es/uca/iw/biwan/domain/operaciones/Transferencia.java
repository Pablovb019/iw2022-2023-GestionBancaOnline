package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Transferencia extends Movimiento {

    private String cuentaOrigen;
    private String cuentaDestino;
    private String beneficiario;
    private String concepto;

    public Transferencia(float importe, LocalDateTime fecha, float balanceRestante, String cuentaOrigen, String cuentaDestino, String beneficiario, String concepto) throws IllegalArgumentException {
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

        if(beneficiario == null || beneficiario.length() == 0) {
            JSONObject json = new JSONObject();
            json.put("message", "El beneficiario no puede ser nulo");
            json.put("field", "beneficiario");
            throw new IllegalArgumentException(json.toString());
        }

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
