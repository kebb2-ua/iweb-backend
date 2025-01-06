package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.repository.BultoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BultoService {

    private final BultoRepository bultoRepository;

    //Nuevo bulto
    public BultoEntity addBulto(BultoEntity bulto) { return this.bultoRepository.save(bulto); }

    //Listado de todos los bultos
    public List<BultoEntity> getAllBultos() { return this.bultoRepository.findAll(); }

    //Listado de los bultos de un pedido
    public List<BultoEntity> getBultosByPedidoId(Integer pedidoId) {
        return bultoRepository.findByPedidoId(pedidoId);
    }
}
