package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.rol.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {

    @Column(nullable = false)
    private String direccion;

    @OneToMany
    @JoinColumn(name = "cliente_id")
    private List<Consulta> consultas;

    @ManyToMany
    private List<Cuenta> cuentas;

    public Cliente(String nombre, String apellidos, LocalDate fechaNacimiento, Double telefono, String dni, String email, Role role, String password, String direccion) {
        super(nombre, apellidos, fechaNacimiento, telefono, dni, email, role, password);
        this.direccion = direccion;
    }

    public Cliente() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
