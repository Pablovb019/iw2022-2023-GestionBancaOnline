package es.uca.iw.biwan.endpoints;

import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.MovimientoService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Estado;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.TransaccionBancaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

@RestController
public class MovimientoCuentaEndpoint {
    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaService cuentaService;

    @PostMapping(value = "/api/movimientos")
    Movimiento addMovimiento(@RequestBody Movimiento nuevoMovimiento) {
        nuevoMovimiento.setId(UUID.randomUUID());
        Cuenta cuenta = cuentaService.findCuentaByIban(nuevoMovimiento.getIban());
        if (cuenta.getBalance() < nuevoMovimiento.getValue().doubleValue() && Objects.equals(nuevoMovimiento.getTransactionType(), TransaccionBancaria.WITHDRAWAL.toString())) {
            nuevoMovimiento.setTransactionStatus(Estado.REJECTED.toString());
            movimientoService.saveMovimiento(nuevoMovimiento, cuenta);

        } else {
            nuevoMovimiento.setTransactionStatus(Estado.ACCEPTED.toString());
            movimientoService.saveMovimiento(nuevoMovimiento, cuenta);
            BigDecimal nuevoBalance;
            if (Objects.equals(nuevoMovimiento.getTransactionType(), TransaccionBancaria.DEPOSIT.toString())) {
                nuevoBalance = BigDecimal.valueOf(cuenta.getBalance()).add(nuevoMovimiento.getValue());
            }
            else {
                nuevoBalance = BigDecimal.valueOf(cuenta.getBalance()).subtract(nuevoMovimiento.getValue());
            }
            cuenta.setBalance(nuevoBalance.setScale(2, RoundingMode.HALF_UP).doubleValue());
            cuentaService.updateBalance(cuenta);
        }
        return nuevoMovimiento;
    }
}

