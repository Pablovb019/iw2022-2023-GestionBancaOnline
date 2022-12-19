package es.uca.iw.biwan.domain.tarjeta;

import java.util.Date;

public class Tarjeta {


    private String numero;
    private Date caducidad;
    private int CVV;
    private float limiteGasto;

    private boolean activa;

    public Tarjeta(String numero, Date caducidad, int CVV, float limiteGasto, boolean activa) {
        this.numero = numero;
        this.caducidad = caducidad;
        this.CVV = CVV;
        this.limiteGasto = limiteGasto;
        this.activa = activa;
    }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public Date getCaducidad() { return caducidad; }

    public void setCaducidad(Date caducidad) { this.caducidad = caducidad; }

    public int getCVV() { return CVV; }

    public void setCVV(int CVV) { this.CVV = CVV; }

    public float getLimiteGasto() { return limiteGasto; }

    public void setLimiteGasto(float limiteGasto) { this.limiteGasto = limiteGasto; }

    public boolean getActiva() { return activa; }

    public void setActiva(boolean activa) { this.activa = activa; }

}
