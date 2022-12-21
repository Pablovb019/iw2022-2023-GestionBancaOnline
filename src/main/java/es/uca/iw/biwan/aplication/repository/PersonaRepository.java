package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}