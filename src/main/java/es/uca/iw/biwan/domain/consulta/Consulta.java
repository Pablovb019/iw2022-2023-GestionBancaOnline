package es.uca.iw.biwan.domain.consulta;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Consulta {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private LocalDate fecha;
}
