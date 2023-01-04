package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.comunicaciones.Anuncio;
import es.uca.iw.biwan.domain.comunicaciones.Noticia;
import es.uca.iw.biwan.domain.comunicaciones.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;


public interface AnuncioRepository extends JpaRepository<Anuncio, UUID> {
    @Modifying
    @Query(
            value = "INSERT INTO anuncio VALUES (:tipo, :uuid, :fecha_inicio, :fecha_fin, :titulo, :cuerpo)",
            nativeQuery = true
    )
    void insertAnuncio(@Param("tipo") String tipo,
                    @Param("uuid") UUID uuid,
                    @Param("fecha_inicio") LocalDate fecha_inicio,
                    @Param("fecha_fin") LocalDate fecha_fin,
                    @Param("titulo") String titulo,
                    @Param("cuerpo") String cuerpo
    );

    @Query(
            value = "SELECT * FROM anuncio WHERE tipo = :tipo",
            nativeQuery = true
    )
    ArrayList<Noticia> findNoticiaByType(@Param("tipo") String tipo);

    @Query(
            value = "SELECT * FROM anuncio WHERE tipo = :tipo",
            nativeQuery = true
    )
    ArrayList<Oferta> findOfertaByType(@Param("tipo") String tipo);

    @Modifying
    @Query(
            value = "DELETE FROM anuncio WHERE uuid = :uuid",
            nativeQuery = true
    )
    void deleteAnuncio(@Param("uuid") UUID uuid);

    @Modifying
    @Query(
            value = "UPDATE anuncio SET tipo = :tipo, fecha_inicio = :fecha_inicio, fecha_fin = :fecha_fin, titulo = :titulo, cuerpo = :cuerpo WHERE uuid = :uuid",
            nativeQuery = true
    )
    void updateAnuncio(@Param("tipo") String tipo,
                    @Param("uuid") UUID uuid,
                    @Param("fecha_inicio") LocalDate fecha_inicio,
                    @Param("fecha_fin") LocalDate fecha_fin,
                    @Param("titulo") String titulo,
                    @Param("cuerpo") String cuerpo
    );
}
