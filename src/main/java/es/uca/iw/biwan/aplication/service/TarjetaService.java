package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TarjetaRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public void update(Tarjeta tarjeta) {
        tarjetaRepository.updateTarjeta(tarjeta.getNumeroTarjeta(), tarjeta.getFechaCaducidad(), tarjeta.getActiva(), tarjeta.getCVV(), tarjeta.getLimiteGasto());
    }

    public String findIbanByNumeroTarjeta(String numero_tarjeta) { return tarjetaRepository.findIbanByNumeroTarjeta(numero_tarjeta); }

    public ArrayList<Tarjeta> findTarjetaByUUID(UUID uuid) { return tarjetaRepository.findTarjetaByUUID(uuid); }
}
