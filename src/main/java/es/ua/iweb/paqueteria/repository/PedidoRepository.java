package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer>{

    List<PedidoEntity> findByRepartidorId(Integer repartidorId);

    List<PedidoEntity> findByFechaRango(Date start, Date end);
}
