package es.uca.iw.biwan.domain.consulta;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ONLINE")
public class Online extends Consulta {
    @Column(length = 16)
    private UUID sala;

    public void setSala(UUID sala) {
        this.sala = sala;
    }

    public UUID getSala() {
        return sala;
    }
}
