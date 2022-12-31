package es.uca.iw.biwan.endpoints;

import es.uca.iw.biwan.aplication.service.CuentaService;
import es.uca.iw.biwan.aplication.service.MovimientoService;
import es.uca.iw.biwan.aplication.service.UsuarioService;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Estado;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.TransaccionBancaria;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Payload;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MovimientoCuentaEndpoint {
    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/api/movimientos")
    Movimiento addMovimiento(@RequestBody Movimiento nuevoMovimiento) {
        nuevoMovimiento.setId(UUID.randomUUID());
        Cuenta cuenta = cuentaService.findCuentaByIban(nuevoMovimiento.getIban());
        if (cuenta.getBalance() < nuevoMovimiento.getValue().doubleValue() && nuevoMovimiento.getTransactionType() == TransaccionBancaria.WITHDRAWAL) {
            nuevoMovimiento.setTransactionStatus(Estado.REJECTED);
            movimientoService.saveMovimiento(nuevoMovimiento, cuenta);
            return nuevoMovimiento;

        } else {
            nuevoMovimiento.setTransactionStatus(Estado.ACCEPTED);
            movimientoService.saveMovimiento(nuevoMovimiento, cuenta);
            BigDecimal nuevoBalance;
            if (nuevoMovimiento.getTransactionType() == TransaccionBancaria.DEPOSIT) {
                nuevoBalance = BigDecimal.valueOf(cuenta.getBalance()).add(nuevoMovimiento.getValue());
            }
            else {
                nuevoBalance = BigDecimal.valueOf(cuenta.getBalance()).subtract(nuevoMovimiento.getValue());
            }
            cuenta.setBalance(nuevoBalance.setScale(2, RoundingMode.HALF_UP).doubleValue());
            cuentaService.updateBalance(cuenta);
            return nuevoMovimiento;
        }

    }
}

