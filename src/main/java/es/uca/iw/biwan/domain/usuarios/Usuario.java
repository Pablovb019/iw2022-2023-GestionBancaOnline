package es.uca.iw.biwan.domain.usuarios;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Usuario {
    @Id
    @GeneratedValue
    @Column(length = 16)
    private UUID id;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private int telefono;
    private String dni;
    private String email;
    private String password;

    public Usuario(String nombre, String apellidos, Date fechaNacimiento, int telefono, String dni, String email, String password) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.dni = dni;
        this.email = email;
        this.password = password;
    }

    public Usuario() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }

    public String getDni() { return dni;}
    public void setDni(String dni) { this.dni = dni; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
