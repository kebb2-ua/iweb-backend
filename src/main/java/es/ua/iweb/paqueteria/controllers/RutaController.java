package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.*;
import es.ua.iweb.paqueteria.entity.*;
import es.ua.iweb.paqueteria.service.RutaService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rutas")
@RequiredArgsConstructor
@CrossOrigin
@Hidden
public class RutaController {

    private final RutaService rutaService;

    @PostMapping
    public ResponseEntity<RutaEntity> createRuta(@RequestBody RutaRequest ruta) {
        return ResponseEntity.ok(rutaService.addRuta(ruta));
    }

    @GetMapping
    public ResponseEntity<List<RutaEntity>> getAllRutas() {
        return ResponseEntity.ok(rutaService.getAllRutas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaEntity> getRutaById(@PathVariable Integer id) {
        return ResponseEntity.ok(rutaService.getRutaById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RutaResponse> updateRuta(@PathVariable Integer id, @RequestBody RutaRequest rutaRequest) {
        RutaEntity updatedRutaEntity = rutaService.updateRuta(id, rutaRequest);

        RutaResponse rutaResponse = RutaResponse.builder()
                .id(updatedRutaEntity.getId())
                .repartidor(mapToUserDTO(updatedRutaEntity.getRepartidor()))
                .fecha(updatedRutaEntity.getFecha())
                .pedidos(updatedRutaEntity.getPedidos() != null ? updatedRutaEntity.getPedidos().stream().map(this::mapToPedidoRequest).collect(Collectors.toList()) : null)
                .build();

        return ResponseEntity.ok(rutaResponse);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Integer id) {
        rutaService.deleteRuta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repartidor/{id}")
    public ResponseEntity<List<RutaEntity>> getRutasByRepartidor(@PathVariable Integer id) {
        return ResponseEntity.ok(rutaService.getRutasByRepartidor(id));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<RutaEntity>> getRutasByFecha(@RequestParam Date fecha) {
        // Limpiar las horas de la fecha para que no se tengan en cuenta
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date fechaSinHora = calendar.getTime();
        return ResponseEntity.ok(rutaService.getRutasByFecha(fechaSinHora));
    }
}