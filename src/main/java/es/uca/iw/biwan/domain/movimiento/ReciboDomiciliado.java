package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

public class ReciboDomiciliado extends Movimiento {

    private LocalDateTime fechaVencimiento;
    private String emisor;
    private String concepto;

    public ReciboDomiciliado(float importe, LocalDateTime fecha, float balanceRestante, LocalDateTime fechaVencimiento, String emisor, String concepto) {
        super(importe, fecha, balanceRestante);
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
