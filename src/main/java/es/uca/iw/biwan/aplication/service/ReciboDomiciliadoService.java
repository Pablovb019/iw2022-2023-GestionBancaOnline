package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.ReciboDomiciliadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboDomiciliadoService {
    private final ReciboDomiciliadoRepository reciboDomiciliadoRepository;

    @Autowired
    public ReciboDomiciliadoService(ReciboDomiciliadoRepository reciboDomiciliadoRepository) {
        this.reciboDomiciliadoRepository = reciboDomiciliadoRepository;
    }
}
