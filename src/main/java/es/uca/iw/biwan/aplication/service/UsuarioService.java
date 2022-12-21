package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.UsuarioRepository;
import es.uca.iw.biwan.domain.usuarios.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void save(Persona persona) {
        usuarioRepository.insertarUsuario(persona.getId(), persona.getNombre(), persona.getApellidos(), persona.getFechaNacimiento(), persona.getTelefono(), persona.getDni(), persona.getEmail(), persona.getPassword());
    }
}
