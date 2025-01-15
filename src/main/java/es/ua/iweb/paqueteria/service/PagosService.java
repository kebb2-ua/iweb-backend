package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.PagosClient;
import es.ua.iweb.paqueteria.dto.NuevoPagoDTO;
import es.ua.iweb.paqueteria.entity.PagoEntity;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.exception.ConflictException;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.repository.PagosRepository;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import es.ua.iweb.paqueteria.type.EstadoType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagosService {
    @Value("${application.host}")
    private String host;

    @Autowired
    private final PagosRepository pagosRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private final PagosClient pagosClient;

    @Transactional
    public Map<String, String> crearPago(PedidoEntity pedido) {
        Optional<PagoEntity> pagoOptional = pagosRepository.findByPedido(pedido);

        // Si el pago ya existe...
        if(pagoOptional.isPresent()) {
            // ...y ha sido aceptado, no se puede volver a pagar
            if(pagoOptional.get().getEstado() == EstadoType.ACEPTADO) {
                throw ConflictException.paymentAlreadyAccepted();
            }

            // ...y ha sido creado hace menos de 10 minutos, devolvemos la URL de la pasarela
            PagoEntity pago = pagoOptional.get();
            long minutos = ChronoUnit.MINUTES.between(pago.getUpdatedAt().toInstant(), new Date().toInstant());
            if(minutos < 10) {
                return Map.of("url", pago.getUrlPasarela());
            }
        }

        // Si ya existe un pago para este pedido, lo recuperamos. Si no, lo creamos.
        PagoEntity pago = pagoOptional.orElse(PagoEntity.builder().pedido(pedido).build());

        // confirmacion de pago = 2 randomUUID seguidos sin guiones
        String confirmacion = (UUID.randomUUID() + UUID.randomUUID().toString()).replace("-", "");

        // Establecemos nuevos valores
        pago.setCodigo(confirmacion);
        pago.setEstado(EstadoType.PENDIENTE);
        pago.setUpdatedAt(new Date());

        // Solicitud a la pasarela de pagos
        NuevoPagoDTO nuevoPagoDTO = NuevoPagoDTO.builder()
                .amount(pedido.getPrecio())
                .currency("EUR")
                .description("Pago del envío " + pedido.getSeguimiento())
                .reference("PAGO-" + pedido.getSeguimiento() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase())
                .url_callback(host.replace("\"", "") + "/pago/confirmar/" + confirmacion)
                .build();

        // Llamada a la pasarela de pagos
        ResponseEntity<Map<String, String>> response = pagosClient.crearPago(nuevoPagoDTO);

        // Guardamos la URL de la pasarela
        pago.setUrlPasarela(response.getBody().get("url"));

        // Guardamos el pago en DB
        PagoEntity pagoFinal = pagosRepository.save(pago);

        // Asociamos el pago al pedido
        pedido.setPago(pagoFinal);
        pedidoRepository.save(pedido);

        return response.getBody();
    }

    @Transactional
    public void confirmarPago(String codigo) {
        PagoEntity pago = pagosRepository.findByCodigo(codigo).orElseThrow(DataNotFoundException::pagoNotFound);

        if(pago.getEstado() == EstadoType.ACEPTADO) {
            throw ConflictException.paymentAlreadyAccepted();
        }

        pago.setEstado(EstadoType.ACEPTADO);
        pagosRepository.save(pago);
    }
}
