package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.CuentaRepository;
import es.uca.iw.biwan.domain.cuenta.Cuenta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public void save(Cuenta cuenta) {
        cuentaRepository.insertCuenta(cuenta.getIBAN(), cuenta.getBalance());
    }

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta updateBalance(Cuenta cuenta, double balance) {
        cuenta.setBalance(balance);
        return cuentaRepository.save(cuenta);
    }

    public ArrayList<Cuenta> findCuentaByUUID(UUID uuid) { return cuentaRepository.findCuentaByUUID(uuid); }

    public Cuenta findCuentaByIban(String Iban) { return  cuentaRepository.findCuentaByIban(Iban); }
}
