package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.PedidoDTO;
import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.entity.DireccionValue;
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

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

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

}
