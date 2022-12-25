package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TransferenciaRepository;
import es.uca.iw.biwan.domain.operaciones.Transferencia;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaService {
    private final TransferenciaRepository transferenciaRepository;

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    public void save(Transferencia transferencia) {
        transferenciaRepository.save(transferencia);
    }

    public void delete(Transferencia transferencia) {
        transferenciaRepository.delete(transferencia);
    }

    public void deleteById(UUID id) {
        transferenciaRepository.deleteById(id);
    }

    public Transferencia findById(UUID id) {
        return transferenciaRepository.findById(id).orElse(null);
    }

    public List<Transferencia> findAll() {
        return transferenciaRepository.findAll();
    }

    public List<Transferencia> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return transferenciaRepository.findAll();
        } else {
            return transferenciaRepository.search(filterText);
        }
    }

    
}
