package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.usuarios.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

}
