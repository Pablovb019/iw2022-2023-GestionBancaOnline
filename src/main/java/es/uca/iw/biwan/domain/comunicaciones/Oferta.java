package es.uca.iw.biwan.domain.comunicaciones;

import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("OFERTA")
public class Oferta extends Anuncio {
}
