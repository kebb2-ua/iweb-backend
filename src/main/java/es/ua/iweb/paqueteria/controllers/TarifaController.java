package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import es.ua.iweb.paqueteria.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
@CrossOrigin
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<TarifaEntity> createTarifa(@RequestBody TarifaEntity tarifa) {
        return ResponseEntity.ok(tarifaService.createTarifa(tarifa));
    }

    @GetMapping
    public ResponseEntity<List<TarifaEntity>> getAllTarifas() {
        return ResponseEntity.ok(tarifaService.getAllTarifas());
    }

    @GetMapping("/peligroso/{peligroso}")
    public ResponseEntity<List<TarifaEntity>> getByPeligroso(@PathVariable Boolean peligroso) {
        return ResponseEntity.ok(tarifaService.getByPeligroso(peligroso));
    }
}
