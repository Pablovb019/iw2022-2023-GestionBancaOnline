package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaService {
    private final OfertaRepository ofertaRepository;

    @Autowired
    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }
}
