package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarifaRepository extends JpaRepository<TarifaEntity, Integer> {
    List<TarifaEntity> findByPeligroso(Boolean peligroso);
}
