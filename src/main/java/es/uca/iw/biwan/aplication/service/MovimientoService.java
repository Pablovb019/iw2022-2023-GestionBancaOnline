package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.MovimientoRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.usuarios.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;

    @Autowired
    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public void saveMovimiento(Movimiento movimiento, Cuenta cuenta) {
        movimientoRepository.saveMovimiento(movimiento.getId(), String.valueOf(movimiento.getTransactionStatus()), movimiento.getIssuer(), String.valueOf(movimiento.getTransactionType()), movimiento.getConcept(), movimiento.getIban(), movimiento.getValue(), cuenta.getUUID());
    }
}
