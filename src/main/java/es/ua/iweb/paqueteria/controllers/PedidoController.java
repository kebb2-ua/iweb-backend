package es.ua.iweb.paqueteria.controllers;


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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/envios")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin
public class PedidoController {
    @Autowired
    private final PedidoService pedidoService;

    @Operation(summary = "Obtiene todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Devuelve una lista con todos los pedidos")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REPARTIDOR')")
    public ResponseEntity<List<PedidoResponse>> getAllPedidos() {
        List<PedidoResponse> pedidos = pedidoService.getAllPedidos();
        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Obtiene todos los pedidos del usuario actual")
    @ApiResponse(responseCode = "200", description = "Devuelve una lista con todos los pedidos del usuario actual")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/owned")
    public ResponseEntity<List<PedidoResponse>> getUserPedidos() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<PedidoResponse> pedidos = pedidoService.getPedidosByUsuario(email);

        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Añade un pedido")
    @ApiResponse(responseCode = "201", description = "Devuelve el pedido añadido")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<NewPedidoResponse> addPedido(@RequestBody @Valid PedidoRequest pedido) {
        String remitente = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.addPedido(remitente, pedido));
    }

    @Operation(summary = "Obtiene un pedido por su número de seguimiento")
    @ApiResponse(responseCode = "200", description = "Devuelve el pedido")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{seguimiento}")
    public ResponseEntity<PedidoResponse> getPedidoBySeguimiento(@PathVariable String seguimiento) {
        return ResponseEntity.ok(pedidoService.getPedido(seguimiento));
    }


    // "pedidos/{idPedido}/asignar/{idRepartidor}": Asigna un repartidor a un pedido
    @Operation(summary = "Asigna un repartidor a un pedido")
    @ApiResponse(responseCode = "200", description = "Devuelve el pedido con el repartidor asignado")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{nSeguimiento}/asignar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PedidoResponse> asignarRepartidor(@PathVariable("nSeguimiento") String nSeguimiento, @RequestParam("emailRepartidor") String emailRepartidor) {
        return ResponseEntity.ok(pedidoService.actualizarRepartidor(nSeguimiento, emailRepartidor));
    }

    @Operation(summary = "Obtiene el estado de un pedido")
    @ApiResponse(responseCode = "200", description = "Devuelve el estado del pedido")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/estado/{seguimiento}")
    public ResponseEntity<EstadoPedidoDTO> getEstadoPedido(@PathVariable String seguimiento) {
        return ResponseEntity.ok(pedidoService.getEstadoPedido(seguimiento));
    }

    @Operation(summary = "Calcula el precio de un envío")
    @ApiResponse(responseCode = "200", description = "Devuelve la tarifa del envío")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/tarifas")
    public ResponseEntity<TarifaResponse> getTarifa(@RequestBody @Valid TarifaRequest tarifaRequest) {
        Float coste = pedidoService.getTarifa(tarifaRequest);
        return ResponseEntity.ok(TarifaResponse.builder().coste_estimado(coste).build());
    }

    @Operation(summary = "Modifica el estado del pedido")
    @ApiResponse(responseCode = "200", description = "Devuelve el pedido con el estado modificado")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{seguimiento}/estado")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REPARTIDOR')")
    public ResponseEntity<PedidoResponse> actualizarEstadoPedido(@PathVariable String seguimiento, @RequestBody @Valid EstadoPedidoRequest estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(seguimiento, estado));
    }
}
