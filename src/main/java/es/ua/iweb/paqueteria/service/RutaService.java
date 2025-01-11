package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.RutaDTO;
import es.ua.iweb.paqueteria.dto.RutaRequest;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.repository.RutaRepository;
import es.ua.iweb.paqueteria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RutaService {
    private final RutaRepository rutaRepository;

    private final PedidoService pedidoService;

    private final UserService userService;


    public RutaEntity addRuta(RutaRequest ruta) {

        RutaEntity nuevaRuta = RutaEntity.builder()
                .fecha(ruta.getFecha())
                .repartidor(userService.getUserById(ruta.getRepartidorId()))
                .build();

        for(Integer idPedido : ruta.getIdsPedidos()) {
            PedidoEntity pedido = pedidoService.getPedidoById(idPedido);
            nuevaRuta.getPedidos().add(pedido);
            pedido.setRuta(nuevaRuta);
        }

        return rutaRepository.save(nuevaRuta);
    }

    public List<RutaEntity> getAllRutas() {
        return rutaRepository.findAll();
    }

    public RutaEntity getRutaById(Integer id) {
        return rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));
    }

    public RutaEntity updateRuta(Integer id, RutaEntity rutaDetails) {
        RutaEntity ruta = getRutaById(id);
        ruta.setFecha(rutaDetails.getFecha());
        ruta.setRepartidor(rutaDetails.getRepartidor());
        return rutaRepository.save(ruta);
    }

    public void deleteRuta(Integer id) {
        RutaEntity ruta = getRutaById(id);
        rutaRepository.delete(ruta);
    }

    public List<RutaEntity> getRutasByRepartidor(Integer repartidorId) {
        return rutaRepository.findByRepartidorId(repartidorId);
    }

    public List<RutaEntity> getRutasByFecha(Date fecha) {
        return rutaRepository.findByFecha(fecha);
    }
}
