package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.EstadoResponse;
import es.ua.iweb.paqueteria.dto.PedidoDTO;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/envios")
@RequiredArgsConstructor
public class PedidoController {
    @Autowired
    private final PedidoService pedidoService;

    @GetMapping({"", "/", "/all"})
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PedidoDTO> addPedido(@RequestBody @Valid PedidoDTO pedido) {
        String remitente = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.addPedido(remitente, pedido));
    }

    // "pedidos/estado/{idPedido}": Devuelve un JSON con el estado actual del pedido
    @GetMapping("/{idPedido}/estado")
    public ResponseEntity<EstadoResponse> getEstadoPedido(@PathVariable String idPedido){
        return ResponseEntity.ok(pedidoService.getEstadoById(Integer.parseInt(idPedido)));
    }

    // "pedidos/{idPedido}/asignar/{idRepartidor}": Asigna un repartidor a un pedido
    @PostMapping("/{idPedido}/asignar")
    public ResponseEntity<PedidoEntity> asignarRepartidor(@PathVariable("idPedido") String idPedido, @RequestParam("emailRepartidor") String emailRepartidor) {
        return ResponseEntity.ok(pedidoService.actualizarRepartidor(idPedido, emailRepartidor));
    }
}
