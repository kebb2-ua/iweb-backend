package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.AlbaranEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlbaranRepository extends JpaRepository<AlbaranEntity, Integer> {

    // Filtro opcional por numAlbaran
    List<AlbaranEntity> findByNumAlbaran(Integer numAlbaran);

    // Filtrar por fecha de emisión
    List<AlbaranEntity> findByFechaEmisionBetween(Date start, Date end);

    // Ordenar por fecha de emisión
    List<AlbaranEntity> findAllByOrderByFechaEmisionAsc();

}
