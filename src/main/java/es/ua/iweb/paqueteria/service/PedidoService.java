package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.*;
import es.ua.iweb.paqueteria.entity.*;
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
import java.util.*;

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

    @Autowired
    private final TarifaService tarifaService;

    @Autowired
    private final PagosService pagosService;

    @Transactional
    public NewPedidoResponse addPedido(String email, PedidoRequest pedido) {
        try {
            UserEntity remitente = userService.getUserByEmail(email);

            TarifaRequest tarifaRequest = TarifaRequest.builder()
                    .peso(pedido.getBultos().stream().map(BultoDTO::getPeso).reduce(0f, Float::sum))
                    .peligroso(pedido.getBultos().stream().anyMatch(BultoDTO::getPeligroso))
                    .build();

            PedidoEntity pedidoEntity = pedidoRepository.save(PedidoEntity.builder()
                    .seguimiento(generarCodigoSeguimiento())
                    .remitente(remitente)
                    .origen(DireccionValue.buildFromDTO(pedido.getOrigen()))
                    .destino(DireccionValue.buildFromDTO(pedido.getDestino()))
                    .estado(EstadoType.PENDIENTE)
                    .estado_ultima_actualizacion(LocalDateTime.now())
                    .precio(getTarifa(tarifaRequest))
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

            // Generamos la entidad PAGO
            pagosService.crearPago(pedidoFinal);

            return NewPedidoResponse.builder()
                    .id_envio(pedidoFinal.getId())
                    .seguimiento(pedidoFinal.getSeguimiento())
                    .fecha_creacion(pedidoFinal.getEstado_ultima_actualizacion())
                    .build();
        } catch (NullPointerException e) {
            throw MalformedObjectException.invalidObject();
        }
    }

    @Transactional(readOnly = true)
    public PedidoResponse getPedido(String seguimiento) {
        PedidoEntity pedido = pedidoRepository.findBySeguimiento(seguimiento).orElseThrow(DataNotFoundException::pedidoNotFound);
        return pedido.toDTO();
    }

    public List<PedidoResponse> getAllPedidos() {
        return this.pedidoRepository.findAll().stream().map(PedidoEntity::toDTO).toList();
    }

    public List<PedidoResponse> getPedidosByUsuario(String email) {
        UserEntity user = userService.getUserByEmail(email);
        return pedidoRepository.findByRemitenteId(user.getId()).stream().map(PedidoEntity::toDTO).toList();
    }

    public List<PedidoEntity> getPedidosByRepartidor(Integer repartidorId) {
        return pedidoRepository.findByRepartidorId(repartidorId);
    }

    public List<PedidoEntity> getPedidosByRutaId(Integer rutaId){ return pedidoRepository.findByRutaId(rutaId); }

    @Transactional
    public PedidoResponse actualizarRepartidor(String nSeguimiento, String emailRepartidor) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findBySeguimiento(nSeguimiento);
        UserEntity repartidor = userService.getUserByEmail(emailRepartidor);

        if (optionalPedido.isPresent() && repartidor != null) {
            PedidoEntity pedido = optionalPedido.get();
            pedido.setRepartidor(repartidor);

            return pedidoRepository.save(pedido).toDTO();


        } else {
            throw new IllegalArgumentException("Pedido con ID " + nSeguimiento + " no encontrado o repartidor con email " + emailRepartidor + " no encontrado .");
        }
    }

    @Transactional(readOnly = true)
    public EstadoPedidoDTO getEstadoPedido(String seguimiento) {
        PedidoEntity pedido = pedidoRepository.findBySeguimiento(seguimiento).orElseThrow(DataNotFoundException::pedidoNotFound);
        return EstadoPedidoDTO.builder()
                .estado(pedido.getEstado())
                .estado_ultima_actualizacion(pedido.getEstado_ultima_actualizacion())
                .build();
    }

    public Float getTarifa(TarifaRequest tarifaRequest) {
        TarifaEntity tarifa = tarifaService.getTarifa();
        Float coste =   tarifa.getPrecioBase() +
                        (tarifa.getPeso() * tarifaRequest.getPeso()) +
                        (tarifaRequest.getPeligroso() ? tarifa.getPeligroso() : 0);
        return Float.max(coste, tarifa.getPrecioMinimo());
    }

    // Devuelve { "url": "string" }
    public Map<String, String> generarPago(String seguimiento) {
        PedidoEntity pedido = pedidoRepository.findBySeguimiento(seguimiento).orElseThrow(DataNotFoundException::pedidoNotFound);
        return pagosService.crearPago(pedido);
    }

    private String generarCodigoSeguimiento() {
        String prefijo = "ENV";

        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String identificadorUnico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return prefijo + "-" + fechaActual + "-" + identificadorUnico;
    }

    @Transactional
    public PedidoResponse actualizarEstadoPedido(String nSeguimiento, EstadoPedidoRequest estado) {
        Optional<PedidoEntity> optionalPedido = pedidoRepository.findBySeguimiento(nSeguimiento);

        if (optionalPedido.isPresent()) {
            PedidoEntity pedido = optionalPedido.get();
            pedido.setEstado(estado.getEstado());
            pedido.setEstado_ultima_actualizacion(estado.getEstado_ultima_actualizacion());
            return pedidoRepository.save(pedido).toDTO();
        } else {
            throw new IllegalArgumentException("Pedido con ID " + nSeguimiento + " no encontrado.");
        }
    }
}
