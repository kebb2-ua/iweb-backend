package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.repository.BultoRepository;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoEntity> getPedidosByRepartidor(Integer repartidorId) {
        return pedidoRepository.findByRepartidorId(repartidorId);
    }

    public List<PedidoEntity> getPedidosByFechaRango(Date fechaInicio, Date fechaFin) {
        return pedidoRepository.findByFechaRango(fechaInicio, fechaFin);
    }


}
