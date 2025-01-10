package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.EstadoPedidoDTO;
import es.ua.iweb.paqueteria.dto.PedidoDTO;
import es.ua.iweb.paqueteria.entity.UserEntity;
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

import java.util.ArrayList;
import java.util.Collections;
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
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        if(pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Añade un pedido")
    @ApiResponse(responseCode = "201", description = "Devuelve el pedido añadido")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<PedidoDTO> addPedido(@RequestBody @Valid PedidoDTO pedido) {
        String remitente = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.addPedido(remitente, pedido));
    }

    @Operation(summary = "Obtiene el estado de un pedido")
    @ApiResponse(responseCode = "200", description = "Devuelve el estado del pedido")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/estado/{id}")
    public ResponseEntity<EstadoPedidoDTO> getEstadoPedido(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.getEstadoPedido(id));
    }
}
