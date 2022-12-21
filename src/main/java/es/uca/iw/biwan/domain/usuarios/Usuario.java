package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.rol.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID uuid;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Double telefono;
    private String dni;
    private String email;
    private String role;
    private String password;



   public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento, Double telefono, String dni, String email, Role role, String password) {
        this.uuid = UUID.randomUUID();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
        this.role = role.toString();
        this.password = password;
    }

    public Usuario() {

    }

    public UUID getUUID() {
        return uuid;
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

    public String getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}