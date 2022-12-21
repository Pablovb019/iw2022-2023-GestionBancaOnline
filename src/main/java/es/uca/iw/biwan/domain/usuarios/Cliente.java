package es.uca.iw.biwan.domain.usuarios;

import java.time.LocalDate;

public class Cliente extends Persona {

    public Cliente(String nombre, String apellidos, LocalDate fechaNacimiento, int telefono, String dni, String email, String password) {
        super(nombre, apellidos, fechaNacimiento, telefono, dni, email, password);
    }

    public void consultarCuentas() {

    }
}
