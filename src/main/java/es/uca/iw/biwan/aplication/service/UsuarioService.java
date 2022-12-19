package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository repo;

    @Autowired
    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }
}
