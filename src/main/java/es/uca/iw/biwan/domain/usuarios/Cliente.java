package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.rol.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {
    @ManyToMany
    private List<Cuenta> cuentas;

    @OneToMany(mappedBy = "cliente")
    private List<Consulta> consultas;
}
