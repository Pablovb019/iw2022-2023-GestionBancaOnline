package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.UsuarioRepository;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void save(Usuario user) {
        usuarioRepository.insertUser(user.getUUID(), user.getNombre(), user.getApellidos(), user.getFechaNacimiento(), user.getTelefono(), user.getDni(), user.getEmail(), user.getRol(), user.getPassword());
    }

    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findUserByEmail(email);
    }

    public Usuario getUser(UUID uuid) {
        return usuarioRepository.findById(uuid).get();
    }
}
