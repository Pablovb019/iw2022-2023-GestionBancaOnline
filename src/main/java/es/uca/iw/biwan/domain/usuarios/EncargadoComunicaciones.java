package es.uca.iw.biwan.domain.usuarios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("ENCARGADO_COMUNICACIONES")
public class EncargadoComunicaciones extends Usuario {

}
