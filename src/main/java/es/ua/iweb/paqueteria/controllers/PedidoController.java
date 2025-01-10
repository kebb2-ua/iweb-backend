package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.EstadoResponse;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.service.PedidoService;
import es.ua.iweb.paqueteria.service.BultoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private PedidoService pedidoService;

    private BultoService bultoService;

    // "pedidos/estado/{idPedido}": Devuelve un JSON con el estado actual del pedido
    @GetMapping("/estado/{idPedido}")
    public ResponseEntity<EstadoResponse> getEstadoPedido(@PathVariable String idPedido){
        return ResponseEntity.ok(pedidoService.getEstadoById(Integer.parseInt(idPedido)));
    }


    // "pedidos/listado" Devuelve el listado de pedidos
    @GetMapping("/listado")
    public ResponseEntity<List<PedidoEntity>> getListadoPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    // "pedidos/repartidor/{idRepartidor}": Listado de pedidos asignados a un repartidor
    @GetMapping("/repartidor/{idRepartidor}")
    public ResponseEntity<List<PedidoEntity>> getPedidosRepartidor(@PathVariable("idRepartidor") String idRepartidor) {
        return ResponseEntity.ok(pedidoService.getPedidosByRepartidor(Integer.parseInt(idRepartidor)));
    }


}
