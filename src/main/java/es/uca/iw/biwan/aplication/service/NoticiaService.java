package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticiaService {
    private final NoticiaRepository noticiaRepository;

    @Autowired
    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }
}
