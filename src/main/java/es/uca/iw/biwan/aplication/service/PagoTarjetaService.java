package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.PagoTarjetaRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.PagoTarjeta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PagoTarjetaService {
    private final PagoTarjetaRepository pagoTarjetaRepository;

    @Autowired
    public PagoTarjetaService(PagoTarjetaRepository pagoTarjetaRepository) {
        this.pagoTarjetaRepository = pagoTarjetaRepository;
    }

    public void savePagoTarjeta(PagoTarjeta pagoTarjeta, Tarjeta tarjeta) {
        pagoTarjetaRepository.savePagoTarjeta(pagoTarjeta.getId(), String.valueOf(pagoTarjeta.getPaymentStatus()), pagoTarjeta.getValue().doubleValue(), String.valueOf(pagoTarjeta.getType()), pagoTarjeta.getShop(), tarjeta.getUUID());
    }

    public ArrayList<PagoTarjeta> findPagosTarjetaByTarjeta(Tarjeta tarjeta) {
        return pagoTarjetaRepository.findPagosTarjeta(tarjeta.getUUID());
    }
}
