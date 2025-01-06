package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.RutaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity, Integer>{
    List<RutaEntity> findByRepartidorId(Integer repartidorId);

    // Filtrar por fecha específica
    List<RutaEntity> findByFecha(Date fecha);

    List<RutaEntity> findByFechaRango(Date start, Date end);
}
