package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.PagoEntity;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagosRepository extends JpaRepository<PagoEntity, Integer> {
    Optional<PagoEntity> findByCodigo(String codigo);
    Optional<PagoEntity> findByPedido(PedidoEntity pedidoEntity);
}
