package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

public interface AnuncioRepository extends JpaRepository<Anuncio, UUID> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Anuncio VALUES (:tipo, :uuid, :fecha_inicio, :fecha_fin, :titulo, :cuerpo)",
            nativeQuery = true
    )
    void insertAnuncio(@Param("tipo") String tipo,
                    @Param("uuid") UUID uuid,
                    @Param("fecha_inicio") LocalDate fecha_inicio,
                    @Param("fecha_fin") LocalDate fecha_fin,
                    @Param("titulo") String titulo,
                    @Param("cuerpo") String cuerpo
    );
}
