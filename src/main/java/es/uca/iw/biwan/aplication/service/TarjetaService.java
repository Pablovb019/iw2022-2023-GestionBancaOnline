package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }
}
