package es.uca.iw.biwan.domain.tarjeta;

import es.uca.iw.biwan.domain.operaciones.Movimiento;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.CreditCardType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Tarjeta {

    @Id
    @GeneratedValue
    private String numeroTarjeta;

    @Column(nullable = false)
    private LocalDate fechaCaducidad;

    @Column(nullable = false)
    private String CVV;

    @Column(nullable = false)
    private Double limiteGasto;

    @Column(nullable = false)
    private Boolean activa;

    @Transient
    private MockNeat mockNeat = MockNeat.threadLocal();

    @OneToMany
    @JoinColumn(name = "tarjeta_id")
    private List<Movimiento> movimientos;


    public Tarjeta() {
        this.numeroTarjeta = mockNeat.creditCards().type(CreditCardType.VISA_16).get();
        this.fechaCaducidad = LocalDate.now().plusYears(5);
        this.CVV = mockNeat.cvvs().get();
        this.limiteGasto = 1000.0;
        this.activa = true;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public Double getLimiteGasto() {
        return limiteGasto;
    }

    public void setLimiteGasto(Double limiteGasto) {
        this.limiteGasto = limiteGasto;
    }
}