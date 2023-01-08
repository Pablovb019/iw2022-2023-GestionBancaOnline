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
    public void saveNoticia(Noticia noticia) {
        anuncioRepository.insertNoticia(noticia.getTipo(), noticia.getUUID(), noticia.getFechaInicio(), noticia.getTitulo(), noticia.getCuerpo());
    }

    @Transactional
    public void saveOferta(Oferta oferta) {
        anuncioRepository.insertOferta(oferta.getTipo(), oferta.getUUID(), oferta.getFechaInicio(), oferta.getFechaFin(), oferta.getTitulo(), oferta.getCuerpo());
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
    public void updateNoticia(Noticia noticia) {
        anuncioRepository.updateNoticia(noticia.getTipo(), noticia.getUUID(), noticia.getFechaInicio(), noticia.getTitulo(), noticia.getCuerpo());
    }

    @Transactional
    public void updateOferta(Oferta oferta) {
        anuncioRepository.updateOferta(oferta.getTipo(), oferta.getUUID(), oferta.getFechaInicio(), oferta.getFechaFin(), oferta.getTitulo(), oferta.getCuerpo());
    }
}
