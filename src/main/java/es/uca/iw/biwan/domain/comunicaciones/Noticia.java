package es.uca.iw.biwan.domain.comunicaciones;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("NOTICIA")
public class Noticia extends Anuncio {
    public Noticia(){

    }
}
