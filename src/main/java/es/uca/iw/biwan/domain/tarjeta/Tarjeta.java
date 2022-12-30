package es.uca.iw.biwan.domain.tarjeta;

import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    private Random random = new Random();

    @OneToMany
    @JoinColumn(name = "tarjeta_id")
    private List<Movimiento> movimientos;


    public Tarjeta(){
        this.numeroTarjeta = "4026" + "33" + RandomStringUtils.randomNumeric(9);
        this.numeroTarjeta += AlgorithmLuhn(this.numeroTarjeta);

        this.fechaCaducidad = LocalDate.now().plusYears(5);
        this.activa = true;

        this.CVV = RandomStringUtils.randomNumeric(3);
        this.limiteGasto = 1000d;
    }

    public String AlgorithmLuhn(String numeroTarjeta){
        int sum = 0;
        boolean alternate = false;
        for (int i = numeroTarjeta.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(numeroTarjeta.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0) ? "0" : String.valueOf(10 - sum % 10);
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