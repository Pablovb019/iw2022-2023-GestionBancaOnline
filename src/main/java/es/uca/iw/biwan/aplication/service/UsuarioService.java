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

    public void save(Usuario usuario) {
        usuarioRepository.insertarUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getTelefono(), usuario.getDni(), usuario.getEmail(), usuario.getPassword());
    }
}
