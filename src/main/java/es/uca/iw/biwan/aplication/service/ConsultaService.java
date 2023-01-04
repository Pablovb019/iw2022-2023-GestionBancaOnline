package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.ConsultaRepository;
import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.consulta.Offline;
import es.uca.iw.biwan.domain.consulta.Online;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Transactional
    public void saveOffline(Offline consulta) {
        consultaRepository.insertConsultaOffline(consulta.getUUID(), consulta.getFecha(), consulta.getTipo(), consulta.getAutor(), consulta.getTexto(), consulta.getCliente().getUUID(), consulta.getGestor().getUUID());
    }

    @Transactional
    public void saveOnline(Online consulta) {
        consultaRepository.insertConsultaOnline(consulta.getUUID(), consulta.getTipo(), consulta.getCliente().getUUID(), consulta.getGestor().getUUID(), consulta.getSala(), consulta.getFecha());
    }

    public ArrayList<Offline> findMensajesClienteGestorOffline(String tipo, UUID cliente_uuid, UUID gestor_uuid) {
        return consultaRepository.findMensajesClienteGestorOffline(tipo, cliente_uuid, gestor_uuid);
    }

    public ArrayList<Online> findMensajesClienteGestorOnline(String tipo, UUID cliente_uuid, UUID gestor_uuid) {
        return consultaRepository.findMensajesClienteGestorOnline(tipo, cliente_uuid, gestor_uuid);
    }
}
