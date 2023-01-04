package es.uca.iw.biwan.domain.tarjeta;

import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.CreditCardType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class Tarjeta {

    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;

    @Column(nullable = false)
    private String numeroTarjeta;

    @Column(nullable = false)
    private LocalDate fechaCaducidad;

    @Column(nullable = false)
    private String CVV;

    @Column(nullable = false)
    private Integer PIN;

    @Column(nullable = false)
    private Double limiteGasto;

    @Column(nullable = false)
    private Boolean activa;

    @Transient
    private MockNeat mockNeat = MockNeat.threadLocal();

    @OneToMany
    @JoinColumn(name = "tarjeta_id")
    private List<PagoTarjeta> pagoTarjetas;

    public Tarjeta() {
        this.uuid = UUID.randomUUID();
        this.numeroTarjeta = mockNeat.creditCards().type(CreditCardType.VISA_16).get();
        this.fechaCaducidad = LocalDate.now().plusYears(2);
        this.CVV = mockNeat.cvvs().get();
        String pin = String.format("%04d", mockNeat.ints().range(1000, 9999).get());
        this.PIN = Integer.valueOf(pin);
        this.limiteGasto = 1000.0;
        this.activa = true;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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

    public Integer getPIN() {
        return PIN;
    }

    public void setPIN(Integer PIN) {
        this.PIN = PIN;
    }

    public Double getLimiteGasto() {
        return limiteGasto;
    }

    public void setLimiteGasto(Double limiteGasto) {
        this.limiteGasto = limiteGasto;
    }
}