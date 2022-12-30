package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.UsuarioRepository;
import es.uca.iw.biwan.domain.usuarios.*;
import es.uca.iw.biwan.domain.usuarios.Gestor;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String getRole(UUID uuid) {
        return usuarioRepository.findRoleByUUID(uuid);
    }

    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findUserByEmail(email);
    }

    public Usuario findUserByDni (String dni) {
        return usuarioRepository.findUserByDni(dni);
    }

    public Usuario findUserByTelefono (Double telefono) { return usuarioRepository.findUserByTelefono(telefono); }

    public ArrayList<Usuario> findUsuarioByRol(String rol) { return usuarioRepository.findUsuarioByRol(rol); }

    // CLIENTE

    @Transactional
    public void saveCliente(Cliente cliente) {
        usuarioRepository.insertCliente(cliente.getUUID(), cliente.getNombre(), cliente.getApellidos(), cliente.getFechaNacimiento(), cliente.getTelefono(), cliente.getDni(), cliente.getEmail(), cliente.getRol(), cliente.getPassword(), cliente.getGestor_id());
    }

    @Transactional
    public void updateCliente(Cliente cliente) {
        usuarioRepository.updateCliente(cliente.getUUID(), cliente.getNombre(), cliente.getApellidos(), cliente.getFechaNacimiento(), cliente.getTelefono(), cliente.getDni(), cliente.getEmail(), cliente.getPassword());
    }

    // GESTOR

    @Transactional
    public void saveGestor(Gestor gestor) {
        usuarioRepository.insertGestor(gestor.getUUID(), gestor.getNombre(), gestor.getApellidos(), gestor.getFechaNacimiento(), gestor.getTelefono(), gestor.getDni(), gestor.getEmail(), gestor.getRol(), gestor.getPassword());
    }

    @Transactional
    public void updateGestor(Gestor gestor) {
        usuarioRepository.updateGestor(gestor.getUUID(), gestor.getNombre(), gestor.getApellidos(), gestor.getFechaNacimiento(), gestor.getTelefono(), gestor.getDni(), gestor.getEmail(), gestor.getPassword());
    }

    // ENCARGADO COMUNICACIONES

    @Transactional
    public void saveEncargado(EncargadoComunicaciones encargado) {
        usuarioRepository.insertEncargado(encargado.getUUID(), encargado.getNombre(), encargado.getApellidos(), encargado.getFechaNacimiento(), encargado.getTelefono(), encargado.getDni(), encargado.getEmail(), encargado.getRol(), encargado.getPassword());
    }

    @Transactional
    public void updateEncargado(EncargadoComunicaciones encargado) {
        usuarioRepository.updateEncargado(encargado.getUUID(), encargado.getNombre(), encargado.getApellidos(), encargado.getFechaNacimiento(), encargado.getTelefono(), encargado.getDni(), encargado.getEmail(), encargado.getPassword());
    }

    // ADMINISTRADOR

    @Transactional
    public void updateAdministrador(Administrador admin) {
        usuarioRepository.updateAdministrador(admin.getUUID(), admin.getNombre(), admin.getApellidos(), admin.getFechaNacimiento(), admin.getTelefono(), admin.getDni(), admin.getEmail(), admin.getPassword());
    }
}
