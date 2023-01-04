package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.cuenta.Cuenta;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {
    @ManyToMany
    private List<Cuenta> cuentas;

    @OneToMany(mappedBy = "cliente")
    private List<Consulta> consultas;
}
