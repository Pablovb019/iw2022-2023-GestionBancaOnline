package es.uca.iw.biwan.aplication.service;

import com.vaadin.flow.component.button.Button;
import es.uca.iw.biwan.aplication.repository.TransferenciaRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.operaciones.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {
    private final TransferenciaRepository transferenciaRepository;

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository){ this.transferenciaRepository = transferenciaRepository; }

    public void saveTransferencia(Transferencia transferencia, Cuenta cuenta) {
        transferenciaRepository.saveTransferencia(transferencia.getId(), String.valueOf(transferencia.getTransactionStatus()), transferencia.getIssuer(), String.valueOf(transferencia.getTransactionType()), transferencia.getConcept(), transferencia.getIban(), transferencia.getValue(),cuenta.getUUID(), transferencia.getIbanDestino());
    }
}
