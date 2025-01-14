package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.RutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity, Integer> {
    List<RutaEntity> findByRepartidorId(Integer repartidorId);
    @Query("SELECT r FROM RutaEntity r WHERE FUNCTION('DATE', r.fecha) = FUNCTION('DATE', :fecha)")
    List<RutaEntity> findByFecha(@Param("fecha") Date fecha);
}