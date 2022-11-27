package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class ReciboDomiciliado extends Movimiento {

    private LocalDateTime fechaVencimiento;
    private String emisor;
    private String concepto;

    public ReciboDomiciliado(float importe, LocalDateTime fecha, float balanceRestante, LocalDateTime fechaVencimiento, String emisor, String concepto) throws IllegalArgumentException {
        super(importe, fecha, balanceRestante);

        if(fechaVencimiento == null) {
            JSONObject json = new JSONObject();
            json.put("message", "La fecha de vencimiento no puede ser nula");
            json.put("field", "fechaVencimiento");
            throw new IllegalArgumentException(json.toString());
        }

        if(emisor == null || emisor.isEmpty()) {
            JSONObject json = new JSONObject();
            json.put("message", "El emisor no puede ser nulo");
            json.put("field", "emisor");
            throw new IllegalArgumentException(json.toString());
        }

        this.fechaVencimiento = fechaVencimiento;
        this.emisor = emisor;
        this.concepto = concepto;
    }
    
    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getConcepto() {
        return concepto;
    }

    
}
