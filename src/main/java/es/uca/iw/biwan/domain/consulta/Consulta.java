package es.uca.iw.biwan.domain.consulta;

import es.uca.iw.biwan.domain.usuarios.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public class Consulta {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;

    @Transient
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    private Usuario cliente;

    @ManyToOne
    private Usuario gestor;

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setGestor(Usuario gestor) {
        this.gestor = gestor;
    }

    public Usuario getGestor() {
        return gestor;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
