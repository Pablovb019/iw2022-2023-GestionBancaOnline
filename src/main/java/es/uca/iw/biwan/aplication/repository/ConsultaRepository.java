package es.uca.iw.biwan.aplication.repository;

import es.uca.iw.biwan.domain.consulta.Consulta;
import es.uca.iw.biwan.domain.consulta.Offline;
import es.uca.iw.biwan.domain.consulta.Online;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {
    @Modifying
    @Query(
            value = "INSERT INTO consulta VALUES (:tipo, :uuid, :autor, :texto, :fecha, :cliente_uuid, :gestor_uuid, NULL)",
            nativeQuery = true
    )
    void insertConsultaOffline(@Param("uuid") UUID uuid,
                        @Param("fecha") LocalDateTime fecha,
                        @Param("tipo") String tipo,
                        @Param("autor") UUID autor,
                        @Param("texto") String texto,
                        @Param("cliente_uuid") UUID cliente_uuid,
                        @Param("gestor_uuid") UUID gestor_uuid
    );

    @Modifying
    @Query(
            value = "INSERT INTO consulta VALUES (:tipo, :uuid, :fecha, NULL, NULL, :sala, :cliente_uuid, :gestor_uuid)",
            nativeQuery = true
    )
    void insertConsultaOnline(@Param("uuid") UUID uuid,
                        @Param("tipo") String tipo,
                        @Param("cliente_uuid") UUID cliente_uuid,
                        @Param("gestor_uuid") UUID gestor_uuid,
                        @Param("sala") UUID sala,
                              @Param("fecha") LocalDateTime fecha
    );

    @Query(
            value = "SELECT * FROM consulta WHERE tipo = :tipo AND cliente_uuid = :cliente_uuid AND gestor_uuid = :gestor_uuid",
            nativeQuery = true
    )
    ArrayList<Offline> findMensajesClienteGestorOffline(@Param("tipo") String tipo,
                                                 @Param("cliente_uuid") UUID cliente_uuid,
                                                 @Param("gestor_uuid") UUID gestor_uuid
    );

    @Query(
            value = "SELECT * FROM consulta WHERE tipo = :tipo AND cliente_uuid = :cliente_uuid AND gestor_uuid = :gestor_uuid",
            nativeQuery = true
    )
    Online findMensajesClienteGestorOnline(@Param("tipo") String tipo,
                                                      @Param("cliente_uuid") UUID cliente_uuid,
                                                      @Param("gestor_uuid") UUID gestor_uuid
    );
}
