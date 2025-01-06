package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import es.ua.iweb.paqueteria.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaEntity createTarifa(TarifaEntity tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public List<TarifaEntity> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    public List<TarifaEntity> getByPeligroso(Boolean peligroso) {
        return tarifaRepository.findByPeligroso(peligroso);
    }
}
