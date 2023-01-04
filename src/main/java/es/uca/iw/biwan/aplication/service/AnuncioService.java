package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.AnuncioRepository;
import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class AnuncioService {
    private final AnuncioRepository anuncioRepository;

    @Autowired
    public AnuncioService(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    @Transactional
    public void save(Anuncio anuncio) {
        anuncioRepository.insertAnuncio(anuncio.getTipo(), anuncio.getUUID(), anuncio.getFechaInicio(), anuncio.getFechaFin(), anuncio.getTitulo(), anuncio.getCuerpo());
    }

    public ArrayList<Noticia> findNoticiaByType(String noticia) {
        return anuncioRepository.findNoticiaByType(noticia);
    }

    public ArrayList<Oferta> findOfertaByType(String oferta) {
        return anuncioRepository.findOfertaByType(oferta);
    }

    @Transactional
    public void delete(Anuncio anuncio) {
        anuncioRepository.deleteAnuncio(anuncio.getUUID());
    }

    @Transactional
    public void update(Anuncio anuncio) {
        anuncioRepository.updateAnuncio(anuncio.getTipo(), anuncio.getUUID(), anuncio.getFechaInicio(), anuncio.getFechaFin(), anuncio.getTitulo(), anuncio.getCuerpo());
    }
}
