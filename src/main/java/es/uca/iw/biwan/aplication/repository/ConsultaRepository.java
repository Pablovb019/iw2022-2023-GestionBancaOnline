package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {
    @Modifying
    @Query(
            value = "INSERT INTO Consulta VALUES (:tipo, :uuid, :autor, :texto, :fecha, :cliente_uuid, :gestor_uuid)",
            nativeQuery = true
    )
    void insertConsulta(@Param("uuid") UUID uuid,
                        @Param("fecha") LocalDateTime fecha,
                        @Param("tipo") String tipo,
                        @Param("autor") UUID autor,
                        @Param("texto") String texto,
                        @Param("cliente_uuid") UUID cliente_uuid,
                        @Param("gestor_uuid") UUID gestor_uuid
    );
}
