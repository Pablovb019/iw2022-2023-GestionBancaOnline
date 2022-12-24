package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.PagoTarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoTarjetaService {
    private final PagoTarjetaRepository pagoTarjetaRepository;

    @Autowired
    public PagoTarjetaService(PagoTarjetaRepository pagoTarjetaRepository) {
        this.pagoTarjetaRepository = pagoTarjetaRepository;
    }
}
