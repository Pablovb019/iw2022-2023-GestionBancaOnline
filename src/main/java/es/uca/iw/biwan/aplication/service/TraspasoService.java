package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TraspasoRepository;
import es.uca.iw.biwan.domain.operaciones.Traspaso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraspasoService {
    private final TraspasoRepository traspasoRepository;

    @Autowired
    public TraspasoService(TraspasoRepository traspasoRepository) {
        this.traspasoRepository = traspasoRepository;
    }

    public void save(Traspaso traspaso) {
        traspasoRepository.save(traspaso);
    }
}
