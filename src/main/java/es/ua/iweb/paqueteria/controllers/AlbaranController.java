package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.entity.AlbaranEntity;
import es.ua.iweb.paqueteria.service.AlbaranService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/albaranes")
@RequiredArgsConstructor
@Hidden
public class AlbaranController {

    private final AlbaranService albaranService;

    // Crear un nuevo albarán
    @PostMapping
    public ResponseEntity<AlbaranEntity> createAlbaran(@RequestBody AlbaranEntity albaran) {
        return ResponseEntity.ok(albaranService.createAlbaran(albaran));
    }

    // Listar todos los albaranes
    @GetMapping
    public ResponseEntity<List<AlbaranEntity>> getAllAlbaranes() {
        return ResponseEntity.ok(albaranService.getAllAlbaranes());
    }

    // Filtrar por número de albarán
    @GetMapping("/num/{numAlbaran}")
    public ResponseEntity<List<AlbaranEntity>> getByNumAlbaran(@PathVariable Integer numAlbaran) {
        return ResponseEntity.ok(albaranService.getByNumAlbaran(numAlbaran));
    }

    // Filtrar por fecha de emisión
    @GetMapping("/fecha")
    public ResponseEntity<List<AlbaranEntity>> getByFechaEmision(
            @RequestParam("start") Date start, @RequestParam("end") Date end) {
        return ResponseEntity.ok(albaranService.getByFechaEmision(start, end));
    }

    // Obtener albaranes ordenados por fecha de emisión
    @GetMapping("/ordenado")
    public ResponseEntity<List<AlbaranEntity>> getSortedByFechaEmision() {
        return ResponseEntity.ok(albaranService.getSortedByFechaEmision());
    }
}
