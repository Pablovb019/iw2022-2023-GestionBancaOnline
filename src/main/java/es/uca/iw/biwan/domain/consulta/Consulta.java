package es.uca.iw.biwan.domain.consulta;

import es.uca.iw.biwan.domain.usuarios.Cliente;
import es.uca.iw.biwan.domain.usuarios.Gestor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public class Consulta {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;

    @Column(nullable = false)
    private UUID autor;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private LocalDate fecha;

    @Transient
    private String tipo;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Gestor gestor;
}
