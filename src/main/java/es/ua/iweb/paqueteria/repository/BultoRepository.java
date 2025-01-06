package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.BultoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BultoRepository extends JpaRepository<BultoEntity, Integer>{
    List<BultoEntity> findByPedidoId(Integer pedidoId);

}
