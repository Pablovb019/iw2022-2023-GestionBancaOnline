package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.TarjetaRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;
import es.uca.iw.biwan.domain.tarjeta.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class TarjetaService {
    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public void save(Tarjeta tarjeta, Cuenta cuenta) {
        tarjetaRepository.insertTarjeta(tarjeta.getUUID(), tarjeta.getNumeroTarjeta(), tarjeta.getFechaCaducidad(), tarjeta.getActiva(), tarjeta.getCVV(), tarjeta.getPIN(), tarjeta.getLimiteGasto(), cuenta.getUUID());
    }

    public void update(Tarjeta tarjeta) {
        tarjetaRepository.updateTarjeta(tarjeta.getNumeroTarjeta(), tarjeta.getFechaCaducidad(), tarjeta.getActiva(), tarjeta.getCVV(), tarjeta.getPIN(), tarjeta.getLimiteGasto());
    }

    @Transactional
    public void delete(Tarjeta tarjeta) { tarjetaRepository.deleteTarjeta(tarjeta.getUUID()); }

    public String findIbanByNumeroTarjeta(String numero_tarjeta) { return tarjetaRepository.findIbanByNumeroTarjeta(numero_tarjeta); }

    public ArrayList<Tarjeta> findTarjetaByUUID(UUID uuid) { return tarjetaRepository.findTarjetaByUUID(uuid); }

    public Tarjeta findTarjetaByNumeroTarjeta(String numeroTarjeta) { return tarjetaRepository.findTarjetaByNumeroTarjeta(numeroTarjeta); }

    public int findTarjetaByCuentaUUID(UUID uuid) { return tarjetaRepository.findTarjetaByCuentaUUID(uuid); }

    public ArrayList<Tarjeta> findTarjetaByCuenta(UUID uuid) { return tarjetaRepository.findTarjetaByCuenta(uuid); }

    public void updateLimiteGasto(Tarjeta tarjeta) { tarjetaRepository.updateLimiteGasto(tarjeta.getLimiteGasto(), tarjeta.getUUID()); }
}
