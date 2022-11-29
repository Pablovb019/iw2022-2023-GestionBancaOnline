package es.uca.iw.biwan.domain.usuarios;

import java.util.Date;

public class Cliente extends Usuario {


    public Cliente(String nombre, String apellidos, Date fechaNacimiento, int telefono, String dni, String email, String password) {
        super(nombre, apellidos, fechaNacimiento, telefono, dni, email, password);
    }

    public void consultarCuentas() {}
}
