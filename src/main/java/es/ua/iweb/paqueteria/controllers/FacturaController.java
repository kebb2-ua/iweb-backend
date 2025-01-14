package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.entity.FacturaEntity;
import es.ua.iweb.paqueteria.service.FacturaService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facturas")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasAuthority('USER')")
@Hidden
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaEntity> createFactura(@RequestBody FacturaEntity factura) {
        return ResponseEntity.ok(facturaService.createFactura(factura));
    }

    @GetMapping
    public ResponseEntity<List<FacturaEntity>> getAllFacturas() {
        return ResponseEntity.ok(facturaService.getAllFacturas());
    }

    @GetMapping("/{numFactura}")
    public ResponseEntity<List<FacturaEntity>> getByNumFactura(@PathVariable Integer numFactura) {
        return ResponseEntity.ok(facturaService.getByNumFactura(numFactura));
    }
}
