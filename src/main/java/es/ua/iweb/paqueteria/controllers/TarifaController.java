package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import es.ua.iweb.paqueteria.service.TarifaService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasAuthority('USER')")
@Hidden
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping
    public ResponseEntity<TarifaEntity> createTarifa(@RequestBody TarifaEntity tarifa) {
        return ResponseEntity.ok(tarifaService.createTarifa(tarifa));
    }
}
