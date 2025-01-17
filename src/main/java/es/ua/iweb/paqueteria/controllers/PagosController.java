package es.ua.iweb.paqueteria.controllers;

import es.ua.iweb.paqueteria.dto.NewPagoRequest;
import es.ua.iweb.paqueteria.service.PagosService;
import es.ua.iweb.paqueteria.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@CrossOrigin
public class PagosController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PagosService pagosService;

    @PostMapping("/generate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Map<String, String>> generate(@RequestBody NewPagoRequest newPagoRequest) {
        return ResponseEntity.ok(pedidoService.generarPago(newPagoRequest.getSeguimiento()));
    }

    @GetMapping("/confirm/{code}")
    public ResponseEntity<HttpStatus> confirm(@PathVariable("code") String code) {
        pagosService.confirmarPago(code);
        return ResponseEntity.ok().build();
    }
}