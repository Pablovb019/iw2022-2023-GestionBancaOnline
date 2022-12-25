package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.AnuncioRepository;
import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import es.uca.iw.biwan.domain.tipoAnuncio.TipoAnuncio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public String findTypeByUUID(UUID uuid) {
        return anuncioRepository.findTypeByUUID(uuid);
    }

    public ArrayList<Noticia> findNoticiaByType(String noticia) {
        return anuncioRepository.findNoticiaByType(noticia);
    }

    public ArrayList<Oferta> findOfertaByType(String oferta) {
        return anuncioRepository.findOfertaByType(oferta);
    }
}
