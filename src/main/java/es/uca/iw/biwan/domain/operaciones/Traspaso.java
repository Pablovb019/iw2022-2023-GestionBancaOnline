package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("TRASPASO")
public class Traspaso extends Movimiento {
    
    private String cuentaOrigen;
    private String cuentaDestinatario;
    private String concepto;

    public static class CuentaInvalidaException extends Exception {
        public CuentaInvalidaException(String message) {
            super(message);
        }
    }

    public Traspaso(double importe, LocalDateTime fecha, double balanceRestante, String cuentaOrigen, String cuentaDestinatario, String concepto) throws CuentaInvalidaException {

        if(cuentaOrigen == null || cuentaOrigen.isEmpty() || !isValidCuenta(cuentaOrigen))
            throw new CuentaInvalidaException("Cuenta origen inválida");
        this.cuentaOrigen = cuentaOrigen;

        if(cuentaDestinatario == null || cuentaDestinatario.isEmpty() || !isValidCuenta(cuentaDestinatario))
            throw new CuentaInvalidaException("Cuenta destino inválida");
        this.cuentaDestinatario = cuentaDestinatario;
        this.concepto = concepto;
    }

    public Traspaso() {

    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestinatario() {
        return cuentaDestinatario;
    }

    public void setCuentaDestinatario(String cuentaDestinatario) {
        this.cuentaDestinatario = cuentaDestinatario;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    private boolean isValidCuenta(String cuenta) {
        // Check if cuenta is a valid iban number of 24 characters
        if(cuenta.length() != 24)
            return false;

        // Check if cuenta has the correct format
        if(!cuenta.matches("[A-Z]{2}[0-9]{22}"))
            return false;

        // Check if cuenta has the correct checksum
        String cuentaSinChecksum = cuenta.substring(0, 2) + cuenta.substring(4);
        int checksum = 0;
        for(int i = 0; i < cuentaSinChecksum.length(); i++) {
            char c = cuentaSinChecksum.charAt(i);
            int valor = 0;
            if(Character.isDigit(c)) {
                valor = Character.getNumericValue(c);
            } else {
                valor = (int) c - 55;
            }
            checksum += valor * Math.pow(2, 5 * (i % 2) + 4 * (i / 2));
        }
        int resto = checksum % 97;
        int checksumCalculado = 98 - resto;
        String checksumCuenta = cuenta.substring(2, 4);
        int checksumCuentaInt = Integer.parseInt(checksumCuenta);
        if(checksumCalculado != checksumCuentaInt)
            return false;

        return true;

    }
}
