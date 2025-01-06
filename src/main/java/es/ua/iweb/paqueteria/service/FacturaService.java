package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.FacturaEntity;
import es.ua.iweb.paqueteria.repository.FacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaEntity createFactura(FacturaEntity factura) {
        return facturaRepository.save(factura);
    }

    public List<FacturaEntity> getAllFacturas() {
        return facturaRepository.findAll();
    }

    public List<FacturaEntity> getByNumFactura(Integer numFactura) {
        return facturaRepository.findByNumFactura(numFactura);
    }
}
