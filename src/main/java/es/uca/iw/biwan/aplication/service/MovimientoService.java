package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.MovimientoRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Movimiento;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public void saveTransferencia(Transferencia transferencia, Cuenta cuentaOrigen) {
        movimientoRepository.saveTransferencia(transferencia.getId(), String.valueOf(transferencia.getTransactionStatus()), transferencia.getIssuer(), String.valueOf(transferencia.getTransactionType()), transferencia.getConcept(), transferencia.getIban(), transferencia.getValue(), transferencia.getIbanDestino(), cuentaOrigen.getUUID());
    }

    public void saveTraspaso(Traspaso traspaso, Cuenta cuentaOrigen) {
        movimientoRepository.saveTraspaso(traspaso.getId(), String.valueOf(traspaso.getTransactionStatus()), traspaso.getIssuer(), String.valueOf(traspaso.getTransactionType()), traspaso.getConcept(), traspaso.getIban(), traspaso.getValue(), traspaso.getIbanDestino(), cuentaOrigen.getUUID());
    }

    public ArrayList<Movimiento> findAllMovimientos(Cuenta cuenta) {
        return movimientoRepository.findAllMovimientos(cuenta.getUUID());
    }

    public ArrayList<Transferencia> findAllTransferencias(Cuenta cuenta) {
        return movimientoRepository.findAllTransferencias(cuenta.getUUID());
    }

    public ArrayList<Traspaso> findAllTraspasos(Cuenta cuenta) {
        return movimientoRepository.findAllTraspasos(cuenta.getUUID());
    }
}
