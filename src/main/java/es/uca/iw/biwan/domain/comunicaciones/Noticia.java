package es.uca.iw.biwan.domain.comunicaciones;

import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("NOTICIA")
public class Noticia extends Anuncio {
    public Noticia(LocalDate fechaInicio, LocalDate fechaFin, String titulo, String cuerpo, TipoAnuncio tipo) {
        super(fechaInicio, fechaFin, titulo, cuerpo, tipo);
    }

    public Noticia() {

    }
}
