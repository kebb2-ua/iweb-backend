package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<FacturaEntity, Integer> {
    List<FacturaEntity> findByNumFactura(Integer numFactura);
}
