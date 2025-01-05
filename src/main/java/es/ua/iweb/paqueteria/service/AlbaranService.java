package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.AlbaranEntity;
import es.ua.iweb.paqueteria.repository.AlbaranRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbaranService {

    private final AlbaranRepository albaranRepository;

    // Crear un nuevo albarán
    public AlbaranEntity createAlbaran(AlbaranEntity albaran) {
        return albaranRepository.save(albaran);
    }

    // Listar todos los albaranes
    public List<AlbaranEntity> getAllAlbaranes() {
        return albaranRepository.findAll();
    }

    // Buscar por número de albarán
    public List<AlbaranEntity> getByNumAlbaran(Integer numAlbaran) {
        return albaranRepository.findByNumAlbaran(numAlbaran);
    }

    // Filtrar por rango de fechas
    public List<AlbaranEntity> getByFechaEmision(Date start, Date end) {
        return albaranRepository.findByFechaEmisionBetween(start, end);
    }

    // Obtener albaranes ordenados por fecha de emisión
    public List<AlbaranEntity> getSortedByFechaEmision() {
        return albaranRepository.findAllByOrderByFechaEmisionAsc();
    }
}
