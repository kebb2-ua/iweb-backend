package es.ua.iweb.paqueteria.controllers;


import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.dto.*;

import es.ua.iweb.paqueteria.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Obtiene todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Devuelve una lista con todos los pedidos")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<PedidoRequest>> getAllPedidos() {
        List<PedidoRequest> pedidos = pedidoService.getAllPedidos();
        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Añade un pedido")
    @ApiResponse(responseCode = "201", description = "Devuelve el pedido añadido")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<PedidoResponse> addPedido(@RequestBody @Valid PedidoRequest pedido) {
        String remitente = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.addPedido(remitente, pedido));
    }


    // "pedidos/{idPedido}/asignar/{idRepartidor}": Asigna un repartidor a un pedido
    @PostMapping("/{idPedido}/asignar")
    public ResponseEntity<PedidoEntity> asignarRepartidor(@PathVariable("idPedido") String idPedido, @RequestParam("emailRepartidor") String emailRepartidor) {
        return ResponseEntity.ok(pedidoService.actualizarRepartidor(idPedido, emailRepartidor));
    }

    @Operation(summary = "Obtiene el estado de un pedido")
    @ApiResponse(responseCode = "200", description = "Devuelve el estado del pedido")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/estado/{id}")
    public ResponseEntity<EstadoPedidoDTO> getEstadoPedido(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.getEstadoPedido(id));
    }

    @Operation(summary = "Calcula el precio de un envío")
    @ApiResponse(responseCode = "200", description = "Devuelve la tarifa del envío")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/tarifas")
    public ResponseEntity<TarifaResponse> getTarifa(@RequestBody @Valid TarifaRequest tarifaRequest) {
        return ResponseEntity.ok(TarifaResponse.builder().coste_estimado(9.99f).build());
    }
}
