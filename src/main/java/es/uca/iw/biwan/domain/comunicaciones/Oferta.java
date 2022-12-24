package es.uca.iw.biwan.domain.comunicaciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("OFERTA")
public class Oferta extends Anuncio {
    private Float precioAnterior;

    private Float precioActual;

    public Oferta(){

    }

    public Float getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Float precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Float getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(Float precioActual) {
        this.precioActual = precioActual;
    }
}
