package es.uca.iw.biwan.domain.operaciones;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("TRANSFERENCIA")
public class Transferencia extends Movimiento {

    private String cuentaOrigen;
    private String cuentaDestino;
    private String beneficiario;
    private String concepto;

    public static class CuentaInvalidaException extends Exception {
        public CuentaInvalidaException(String message) {
            super(message);
        }
    }

    public static class BeneficiarioInvalidoException extends Exception {
        public BeneficiarioInvalidoException(String message) {
            super(message);
        }
    }

    public Transferencia(double importe, LocalDateTime fecha, double balanceRestante, String cuentaOrigen, String cuentaDestino, String beneficiario, String concepto) throws BeneficiarioInvalidoException, CuentaInvalidaException {

        if(cuentaOrigen == null || cuentaOrigen.isEmpty() || !isValidCuenta(cuentaOrigen))
            throw new CuentaInvalidaException("Cuenta origen no puede estar vacía");
        this.cuentaOrigen = cuentaOrigen;

        if(cuentaDestino == null || cuentaDestino.isEmpty() || !isValidCuenta(cuentaDestino))
            throw new CuentaInvalidaException("Cuenta destino no puede estar vacía");
        
        if(cuentaOrigen.equals(cuentaDestino))
            throw new CuentaInvalidaException("Cuenta origen y destino no pueden ser iguales");
        this.cuentaDestino = cuentaDestino;

        if(beneficiario == null || beneficiario.isEmpty())
            throw new BeneficiarioInvalidoException("El beneficiario no puede estar vacío");

        this.beneficiario = beneficiario;
        this.concepto = concepto;
    }

    public Transferencia() {

    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
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
