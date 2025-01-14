package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    @Autowired
    private final TarifaRepository tarifaRepository;

    public TarifaEntity createTarifa(TarifaEntity tarifa) {
        tarifa.setId(1); // Solo debe haber uno
        return tarifaRepository.save(tarifa);
    }

    public TarifaEntity getTarifa() {
        return tarifaRepository.findById(1).get();
    }
}
