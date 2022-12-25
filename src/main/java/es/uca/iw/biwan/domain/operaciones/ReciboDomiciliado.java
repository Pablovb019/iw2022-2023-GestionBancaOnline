package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("RECIBO_DOMICILIADO")
public class ReciboDomiciliado extends Movimiento {

    private LocalDateTime fechaVencimiento;
    private String emisor;
    private String concepto;

    public static class EmisorInvalidoException extends Exception {
        public EmisorInvalidoException(String message) {
            super(message);
        }
    }

    public static class FechaVencimientoInvalidaException extends Exception {
        public FechaVencimientoInvalidaException(String message) {
            super(message);
        }
    }

    public ReciboDomiciliado(float importe, LocalDateTime fecha, float balanceRestante, LocalDateTime fechaVencimiento, String emisor, String concepto) throws ImporteInvalidoException, FechaInvalidaException, BalanceRestanteInvalidoException, FechaVencimientoInvalidaException, EmisorInvalidoException {
        super(importe, fecha, balanceRestante);

        if(fechaVencimiento == null)
            throw new FechaVencimientoInvalidaException("La fecha de vencimiento no puede ser nula");

        if(fechaVencimiento.isBefore(fecha))
            throw new FechaVencimientoInvalidaException("La fecha de vencimiento no puede ser anterior a la fecha del recibo");
        
        this.fechaVencimiento = fechaVencimiento;

        if(emisor == null || emisor.isEmpty())
            throw new EmisorInvalidoException("El emisor no puede ser nulo o vacío");
        this.emisor = emisor;
        this.concepto = concepto;
    }

    public ReciboDomiciliado() {

    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
