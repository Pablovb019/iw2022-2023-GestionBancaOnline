package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.rol.Role;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol")
public class Usuario {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;
    @Column(nullable = false)
    private LocalDate fechaNacimiento;
    @Column(nullable = false)
    @Unique
    private Double telefono;
    @Column(nullable = false)
    @Unique
    private String dni;
    @Column(nullable = false)
    @Unique
    private String email;
    @Column(nullable = false)
    private String password;
    @Transient
    private String rol;
    @Column
    private UUID gestor_id;
    @Column
    private UUID cliente_id;

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getTelefono() {
        return telefono;
    }

    public void setTelefono(Double telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(Role role) {
        this.rol = role.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public UUID getGestor_id() {
        return gestor_id;
    }

    public void setGestor_id(UUID gestor_id) {
        this.gestor_id = gestor_id;
    }

    public UUID getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(UUID cliente_id) {
        this.cliente_id = cliente_id;
    }
}