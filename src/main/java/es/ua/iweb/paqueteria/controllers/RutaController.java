package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.service.RutaService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rutas")
@RequiredArgsConstructor
@CrossOrigin
@Hidden
public class RutaController {

    private final RutaService rutaService;

    @PostMapping
    public ResponseEntity<RutaEntity> createRuta(@RequestBody RutaEntity ruta) {
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
    public ResponseEntity<RutaEntity> updateRuta(@PathVariable Integer id, @RequestBody RutaEntity ruta) {
        return ResponseEntity.ok(rutaService.updateRuta(id, ruta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Integer id) {
        rutaService.deleteRuta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/origen/{ciudad}")
    public ResponseEntity<List<RutaEntity>> getRutasByCiudadOrigen(@PathVariable String ciudad) {
        return ResponseEntity.ok(rutaService.getRutasByCiudadOrigen(ciudad));
    }

    @GetMapping("/destino/{ciudad}")
    public ResponseEntity<List<RutaEntity>> getRutasByCiudadDestino(@PathVariable String ciudad) {
        return ResponseEntity.ok(rutaService.getRutasByCiudadDestino(ciudad));
    }

    @GetMapping("/repartidor/{id}")
    public ResponseEntity<List<RutaEntity>> getRutasByRepartidor(@PathVariable Integer id) {
        return ResponseEntity.ok(rutaService.getRutasByRepartidor(id));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<RutaEntity>> getRutasByFecha(@RequestParam Date fecha) {
        return ResponseEntity.ok(rutaService.getRutasByFecha(fecha));
    }
}