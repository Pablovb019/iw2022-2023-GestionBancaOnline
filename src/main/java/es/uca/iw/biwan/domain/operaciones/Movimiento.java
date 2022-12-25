package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "tipo_movimiento")
public class Movimiento {

    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;
    @Column(nullable = false)
    protected float importe;
    @Column(nullable = false)
    protected LocalDateTime fecha;
    @Column(nullable = false)
    protected float balanceRestante;

    public static class ImporteInvalidoException extends Exception {
        public ImporteInvalidoException(String message) {
            super(message);
        }
    }

    public static class FechaInvalidaException extends Exception {
        public FechaInvalidaException(String message) {
            super(message);
        }
    }

    public static class BalanceRestanteInvalidoException extends Exception {
        public BalanceRestanteInvalidoException(String message) {
            super(message);
        }
    }

    public Movimiento(float importe, LocalDateTime fecha, float balanceRestante) throws ImporteInvalidoException, FechaInvalidaException, BalanceRestanteInvalidoException {
        this.uuid = UUID.randomUUID();

        if(importe == 0)
            throw new ImporteInvalidoException("El importe no puede ser 0");
        this.importe = importe;

        if(fecha == null || fecha.isAfter(LocalDateTime.now()))
            throw new FechaInvalidaException("La fecha no puede ser nula");
        this.fecha = fecha;

        if(balanceRestante <= 0)
            throw new BalanceRestanteInvalidoException("No dispone de suficiente balance");
        this.balanceRestante = balanceRestante;
    }

    public Movimiento() {

    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public float getBalanceRestante() {
        return balanceRestante;
    }

    public void setBalanceRestante(float balanceRestante) {
        this.balanceRestante = balanceRestante;
    }

}

