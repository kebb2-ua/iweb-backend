package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.PedidoDTO;
import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.entity.DireccionValue;
import es.ua.iweb.paqueteria.dto.EstadoResponse;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.exception.MalformedObjectException;
import es.ua.iweb.paqueteria.repository.BultoRepository;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import es.ua.iweb.paqueteria.type.EstadoType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    public PedidoEntity getPedidoById(Integer pedidoId) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findById(pedidoId);
        return optionalPedido.orElseThrow(() -> new IllegalArgumentException("Pedido con ID " + pedidoId + " no encontrado."));
    }

    @Autowired
    private final BultoRepository bultoRepository;

    @Autowired
    private final UserService userService;

    @Transactional
    public PedidoDTO addPedido(String email, PedidoDTO pedido) {
        try {
            UserEntity remitente = userService.getUserByEmail(email);

            PedidoEntity pedidoEntity = pedidoRepository.save(PedidoEntity.builder()
                    .remitente(remitente)
                    .origen(DireccionValue.buildFromDTO(pedido.getOrigen()))
                    .destino(DireccionValue.buildFromDTO(pedido.getDestino()))
                    .estado(EstadoType.PENDIENTE)
                    .estado_ultima_actualizacion(LocalDateTime.now())
                    .precio(10) // todo
                    .observaciones(pedido.getObservaciones())
                    .build());

            List<BultoEntity> bultos = pedido.getBultos().stream().map(bulto ->
                    BultoEntity.builder()
                            .peso(bulto.getPeso())
                            .altura(bulto.getAltura())
                            .ancho(bulto.getAncho())
                            .profundidad(bulto.getProfundidad())
                            .peligroso(bulto.getPeligroso())
                            .pedido(pedidoEntity)
                            .build()
            ).toList();

            bultos = bultoRepository.saveAll(bultos);
            pedidoEntity.setBultos(bultos);

            return pedidoRepository.save(pedidoEntity).toDTO();
        } catch (NullPointerException e) {
            throw MalformedObjectException.invalidObject();
        }
    }

    public List<PedidoDTO> getAllPedidos() {
        return this.pedidoRepository.findAll().stream().map(PedidoEntity::toDTO).toList();
    }

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
