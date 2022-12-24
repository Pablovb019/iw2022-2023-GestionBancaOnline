package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.AnuncioRepository;
import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {
    private final AnuncioRepository anuncioRepository;

    @Autowired
    public AnuncioService(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    public void save(Anuncio anuncio) {
        anuncioRepository.insertAnuncio(anuncio.getTipo(), anuncio.getUUID(), anuncio.getFechaInicio(), anuncio.getFechaFin(), anuncio.getTitulo(), anuncio.getCuerpo());
    }
}
