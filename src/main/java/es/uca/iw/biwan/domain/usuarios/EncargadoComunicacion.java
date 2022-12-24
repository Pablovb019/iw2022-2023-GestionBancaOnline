package es.uca.iw.biwan.domain.usuarios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ENCARGADO_COMUNICACION")
public class EncargadoComunicacion extends Usuario {

}
