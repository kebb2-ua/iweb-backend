package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.RutaRequest;
import es.ua.iweb.paqueteria.entity.PedidoEntity;
import es.ua.iweb.paqueteria.entity.RutaEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.repository.PedidoRepository;
import es.ua.iweb.paqueteria.repository.RutaRepository;
import es.ua.iweb.paqueteria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RutaService {
    private final RutaRepository rutaRepository;

    private final PedidoService pedidoService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final PedidoRepository pedidoRepository;


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

    public RutaEntity asignarPedido(Integer idRuta, Integer idPedido) {
        RutaEntity ruta = rutaRepository.findById(idRuta)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + idRuta));

        PedidoEntity pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));

        ruta.getPedidos().add(pedido);
        return rutaRepository.save(ruta);
    }

    public void deleteRuta(Integer id) {
        RutaEntity ruta = getRutaById(id);
        rutaRepository.delete(ruta);
    }

    public List<RutaEntity> getRutasByRepartidor(Integer repartidorId) {
        return rutaRepository.findByRepartidorId(repartidorId);
    }

    public Integer getRepartidorIdByEmail(String email) {
        UserEntity repartidor = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con el email: " + email));
        return repartidor.getId();
    }

    public List<RutaEntity> getRutasByFecha(Date fecha) {
        return rutaRepository.findByFecha(fecha);
    }
}
