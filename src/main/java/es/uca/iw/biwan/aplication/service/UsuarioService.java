package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.UsuarioRepository;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void save(Usuario user) {
        usuarioRepository.insertUser(user.getUUID(), user.getNombre(), user.getApellidos(), user.getFechaNacimiento(), user.getTelefono(), user.getDni(), user.getEmail(), user.getRole(), user.getPassword());
    }
}
