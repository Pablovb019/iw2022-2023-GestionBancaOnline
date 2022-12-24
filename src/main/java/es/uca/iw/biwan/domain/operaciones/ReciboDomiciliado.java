package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("RECIBO_DOMICILIADO")
public class ReciboDomiciliado extends Movimiento {

    private LocalDate fechaVencimiento;
    private String emisor;
    private String concepto;

    public ReciboDomiciliado(float importe, LocalDate fecha, float balanceRestante, LocalDate fechaVencimiento, String emisor, String concepto) {
        super(importe, fecha, balanceRestante);

        this.fechaVencimiento = fechaVencimiento;
        this.emisor = emisor;
        this.concepto = concepto;
    }

    public ReciboDomiciliado() {

    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
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
