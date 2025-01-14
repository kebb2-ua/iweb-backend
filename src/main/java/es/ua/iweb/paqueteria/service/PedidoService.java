package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.EstadoPedidoDTO;
import es.ua.iweb.paqueteria.dto.PedidoRequest;
import es.ua.iweb.paqueteria.dto.NewPedidoResponse;
import es.ua.iweb.paqueteria.dto.PedidoResponse;
import es.ua.iweb.paqueteria.entity.BultoEntity;
import es.ua.iweb.paqueteria.entity.DireccionValue;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.exception.MalformedObjectException;
import es.ua.iweb.paqueteria.repository.BultoRepository;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import es.ua.iweb.paqueteria.type.EstadoType;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public NewPedidoResponse addPedido(String email, PedidoRequest pedido) {
        try {
            UserEntity remitente = userService.getUserByEmail(email);

            PedidoEntity pedidoEntity = pedidoRepository.save(PedidoEntity.builder()
                    .seguimiento(generarCodigoSeguimiento())
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

            PedidoEntity pedidoFinal = pedidoRepository.save(pedidoEntity);
            return NewPedidoResponse.builder()
                    .id_envio(pedidoFinal.getId())
                    .fecha_creacion(pedidoFinal.getEstado_ultima_actualizacion())
                    .build();
        } catch (NullPointerException e) {
            throw MalformedObjectException.invalidObject();
        }
    }

    public List<PedidoResponse> getAllPedidos() {
        return this.pedidoRepository.findAll().stream().map(PedidoEntity::toDTO).toList();
    }

    public List<PedidoEntity> getPedidosByRepartidor(Integer repartidorId) {
        return pedidoRepository.findByRepartidorId(repartidorId);
    }

    public List<PedidoEntity> getPedidosByRutaId(Integer rutaId){ return pedidoRepository.findByRutaId(rutaId); }

    @Transactional
    public PedidoEntity actualizarRepartidor(String idPedido, String emailRepartidor) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findById(Integer.parseInt(idPedido));
        UserEntity repartidor = userService.getUserByEmail(emailRepartidor);

        if (optionalPedido.isPresent() && repartidor != null) {
            PedidoEntity pedido = optionalPedido.get();
            pedido.setRepartidor(repartidor);
            return pedidoRepository.save(pedido);
        } else {
            throw new IllegalArgumentException("Pedido con ID " + idPedido + " no encontrado o repartidor con email " + emailRepartidor + " no encontrado .");
        }
    }

    @Transactional(readOnly = true)
    public EstadoPedidoDTO getEstadoPedido(Integer id) {
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(DataNotFoundException::pedidoNotFound);
        return EstadoPedidoDTO.builder()
                .estado(pedido.getEstado())
                .estado_ultima_actualizacion(pedido.getEstado_ultima_actualizacion())
                .build();
    }

    private String generarCodigoSeguimiento() {
        String prefijo = "ENV";

        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String identificadorUnico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return prefijo + "-" + fechaActual + "-" + identificadorUnico;
    }
}
