package es.uca.iw.biwan.domain.movimiento;

import java.time.LocalDateTime;

public class PagoTarjeta extends Movimiento {

    private String informacion;
    private String establecimiento;

    public PagoTarjeta(float importe, LocalDateTime fecha, float balanceRestante, String informacion, String establecimiento) {
        super(importe, fecha, balanceRestante);
        this.informacion = informacion;
        this.establecimiento = establecimiento;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }
    
}
