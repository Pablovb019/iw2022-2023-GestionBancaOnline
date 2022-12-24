package es.uca.iw.biwan.aplication.service;

import es.uca.iw.biwan.aplication.repository.GestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GestorService {
    private final GestorRepository gestorRepository;
    @Autowired
    public GestorService(GestorRepository gestorRepository) {
        this.gestorRepository = gestorRepository;
    }
}