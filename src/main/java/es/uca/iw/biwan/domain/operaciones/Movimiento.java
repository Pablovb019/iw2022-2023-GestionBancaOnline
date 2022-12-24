package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDate;
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
    protected LocalDate fecha;
    @Column(nullable = false)
    protected float balanceRestante;

    public Movimiento(float importe, LocalDate fecha, float balanceRestante) {
        this.uuid = UUID.randomUUID();
        this.importe = importe;
        this.fecha = fecha;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getBalanceRestante() {
        return balanceRestante;
    }

    public void setBalanceRestante(float balanceRestante) {
        this.balanceRestante = balanceRestante;
    }

}

