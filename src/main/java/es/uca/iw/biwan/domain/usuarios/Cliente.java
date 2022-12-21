package es.uca.iw.biwan.domain.usuarios;

import es.uca.iw.biwan.domain.rol.Role;

import java.time.LocalDate;

public class Cliente extends Usuario {

    public Cliente(String nombre, String apellidos, LocalDate fechaNacimiento, double telefono, String dni, String email, String password) {
        super(nombre, apellidos, fechaNacimiento, telefono, dni, email, Role.CLIENTE, password);
    }

    public void consultarCuentas() {

    }
}
