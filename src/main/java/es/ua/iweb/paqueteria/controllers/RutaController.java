package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.*;
import es.ua.iweb.paqueteria.entity.*;
import es.ua.iweb.paqueteria.service.RutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rutas")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin
public class RutaController {

    private final RutaService rutaService;

    @Operation(summary = "Añade una nueva ruta")
    @ApiResponse(responseCode = "201", description = "Devuelve la ruta añadida")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<RutaResponse> createRuta(@RequestBody @Valid RutaRequest ruta) {
        RutaEntity nuevaRuta = rutaService.addRuta(ruta);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToRutaResponse(nuevaRuta));
    }

    @Operation(summary = "Obtiene todas las rutas")
    @ApiResponse(responseCode = "200", description = "Devuelve una lista de rutas")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RutaResponse>> getAllRutas() {
        List<RutaResponse> rutas = rutaService.getAllRutas().stream()
                .map(this::mapToRutaResponse)
                .collect(Collectors.toList());
        return rutas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(rutas);
    }

    @Operation(summary = "Asigna un pedido a una ruta")
    @ApiResponse(responseCode = "200", description = "Devuelve la ruta con el pedido asignado")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{idRuta}/asignar")
    public ResponseEntity<RutaResponse> asignarPedido(@PathVariable Integer idRuta, @RequestParam Integer idPedido) {
        RutaEntity rutaActualizada = rutaService.asignarPedido(idRuta, idPedido);
        return ResponseEntity.ok(mapToRutaResponse(rutaActualizada));
    }

    @Operation(summary = "Obtiene la ruta del día del repartidor")
    @ApiResponse(responseCode = "200", description = "Devuelve la ruta del día con los seguimientos")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('REPARTIDOR')")
    @GetMapping("/diaria")
    public ResponseEntity<RutaResponse> getRutaDelDia() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer repartidor = rutaService.getRepartidorIdByEmail(email);

        Date fecha = java.sql.Date.valueOf(LocalDate.now());
        RutaEntity ruta = rutaService.getRutaDelDia(repartidor, fecha);

        return ResponseEntity.ok(mapToRutaResponse(ruta));
    }

    private RutaResponse mapToRutaResponse(RutaEntity rutaEntity) {
        return RutaResponse.builder()
                .id(rutaEntity.getId())
                .repartidor(mapToUserDTO(rutaEntity.getRepartidor()))
                .fecha(rutaEntity.getFecha())
                .pedidos(rutaEntity.getPedidos() != null ? rutaEntity.getPedidos().stream().map(this::mapToPedidoRequest).collect(Collectors.toList()) : null)
                .build();
    }

    private UserDTO mapToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .nif(userEntity.getNif())
                .nombre(userEntity.getNombre())
                .apellidos(userEntity.getApellidos())
                .razonSocial(userEntity.getRazonSocial())
                .email(userEntity.getEmail())
                .build();
    }

    private PedidoRequest mapToPedidoRequest(PedidoEntity pedidoEntity) {
        return PedidoRequest.builder()
                .origen(pedidoEntity.getOrigen().toDTO())
                .destino(pedidoEntity.getDestino().toDTO())
                .bultos(pedidoEntity.getBultos() != null ? pedidoEntity.getBultos().stream().map(BultoEntity::toDTO).collect(Collectors.toList()) : null)
                .observaciones(pedidoEntity.getObservaciones())
                .build();
    }
}