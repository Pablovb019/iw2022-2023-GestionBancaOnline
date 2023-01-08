package es.uca.iw.biwan.domain.comunicaciones;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("OFERTA")
public class Oferta extends Anuncio {

    @Column
    private LocalDate fechaFin;

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
