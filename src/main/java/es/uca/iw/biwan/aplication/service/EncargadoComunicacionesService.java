package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.EncargadoComunicacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncargadoComunicacionesService {
    private final EncargadoComunicacionesRepository encargadoComunicacionRepository;

    @Autowired
    public EncargadoComunicacionesService(EncargadoComunicacionesRepository encargadoComunicacionRepository) {
        this.encargadoComunicacionRepository = encargadoComunicacionRepository;
    }
}
