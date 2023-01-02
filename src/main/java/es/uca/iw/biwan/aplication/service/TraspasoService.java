package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TraspasoRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Traspaso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraspasoService {
    private final TraspasoRepository traspasoRepository;

    @Autowired
    public TraspasoService(TraspasoRepository traspasoRepository) { this.traspasoRepository = traspasoRepository; }

    public void saveTraspaso(Traspaso traspaso, Cuenta cuenta) {
        traspasoRepository.saveTraspaso(traspaso.getId(), String.valueOf(traspaso.getTransactionStatus()), traspaso.getIssuer(), String.valueOf(traspaso.getTransactionType()), traspaso.getConcept(), traspaso.getIban(), traspaso.getValue(),cuenta.getUUID(), traspaso.getIbanDestino());
    }
}
