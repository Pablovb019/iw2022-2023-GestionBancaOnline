package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.consulta.Consulta;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("GESTOR")
public class Gestor extends Usuario {
    @OneToMany
    @JoinColumn(name = "gestor_id")
    private List<Consulta> consultas;

}