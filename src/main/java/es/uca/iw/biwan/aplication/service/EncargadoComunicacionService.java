package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.EncargadoComunicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncargadoComunicacionService {
    private final EncargadoComunicacionRepository encargadoComunicacionRepository;

    @Autowired
    public EncargadoComunicacionService(EncargadoComunicacionRepository encargadoComunicacionRepository) {
        this.encargadoComunicacionRepository = encargadoComunicacionRepository;
    }
}
