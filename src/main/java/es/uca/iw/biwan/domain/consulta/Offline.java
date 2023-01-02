package es.uca.iw.biwan.domain.consulta;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("OFFLINE")
public class Offline extends Consulta {
    @Column(length = 16)
    private UUID autor;

    @Column
    private String texto;

    public void setAutor(UUID autor) {
        this.autor = autor;
    }

    public UUID getAutor() {
        return autor;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
