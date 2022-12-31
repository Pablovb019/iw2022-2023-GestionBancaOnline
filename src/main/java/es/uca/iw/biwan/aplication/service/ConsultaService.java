package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.ConsultaRepository;
import es.uca.iw.biwan.domain.consulta.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public void save(Consulta consulta) {
        consultaRepository.insertConsulta(consulta.getUUID(), consulta.getFecha(), consulta.getTipo(), consulta.getAutor(), consulta.getTexto(), consulta.getCliente().getUUID(), consulta.getGestor().getUUID());
    }

    public ArrayList<Consulta> findMensajesClienteGestor(String tipo, UUID cliente_uuid, UUID gestor_uuid) {
        return consultaRepository.findMensajesClienteGestor(tipo, cliente_uuid, gestor_uuid);
    }
}
