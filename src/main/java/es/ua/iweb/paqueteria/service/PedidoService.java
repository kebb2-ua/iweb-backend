package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.EstadoResponse;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import es.ua.iweb.paqueteria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;

    public PedidoEntity getPedidoById(Integer pedidoId) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findById(pedidoId);
        return optionalPedido.orElseThrow(() -> new IllegalArgumentException("Pedido con ID " + pedidoId + " no encontrado."));
    }

    public PedidoEntity addPedido(PedidoEntity pedido) { return this.pedidoRepository.save(pedido); }

    public List<PedidoEntity> getAllPedidos() { return this.pedidoRepository.findAll(); }

    public List<PedidoEntity> getPedidosByRepartidor(Integer repartidorId) {
        return pedidoRepository.findByRepartidorId(repartidorId);
    }

    public List<PedidoEntity> getPedidosByRutaId(Integer rutaId){ return pedidoRepository.findByRutaId(rutaId); }

    public EstadoResponse getEstadoById(Integer estadoId) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findById(estadoId);

        if(optionalPedido.isPresent()) {
            PedidoEntity pedido = optionalPedido.get();

            return EstadoResponse.builder().estado_actual(pedido.getEstado().name()).ultima_actualizacion(new Date()).build();
        }else{
            throw new IllegalArgumentException("Pedido con ID " + estadoId + " no encontrado.");
        }
    }

    public PedidoEntity actualizarRepartidor(String idPedido, String emailRepartidor) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findById(Integer.parseInt(idPedido));
        Optional<UserEntity> optionalRepartidor = userRepository.findByEmail(emailRepartidor);

        if(optionalPedido.isPresent()) {
            PedidoEntity pedido = optionalPedido.get();
            pedido.setRepartidor(Integer.parseInt(idRepartidor));
            return pedidoRepository.save(pedido);
        }else{
            throw new IllegalArgumentException("Pedido con ID " + idPedido + " no encontrado.");
        }
    }
}
