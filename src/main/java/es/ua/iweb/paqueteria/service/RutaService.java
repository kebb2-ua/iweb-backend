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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        }

       RutaEntity nRuta = rutaRepository.save(nuevaRuta);
        return nRuta;
    }

    public List<RutaEntity> getAllRutas() {
        return rutaRepository.findAll();
    }

    @Transactional
    public RutaEntity asignarPedido(Integer idRuta, Integer idPedido) {
        RutaEntity ruta = rutaRepository.findById(idRuta)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + idRuta));

        PedidoEntity pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));

        pedido.setRuta(ruta);

        ruta.getPedidos().add(pedido);
        return rutaRepository.save(ruta);
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

    public RutaEntity getRutaDelDia(Integer repartidor, Date fecha) {
        List<RutaEntity> rutas = rutaRepository.findByRepartidorId(repartidor).stream()
                .filter(ruta -> {
                    Date fechaRuta = ruta.getFecha();
                    Date fechaComparar = fecha;

                    return getYear(fechaRuta) == getYear(fechaComparar) &&
                            getMonth(fechaRuta) == getMonth(fechaComparar) &&
                            getDate(fechaRuta) == getDate(fechaComparar);
                })
                .collect(Collectors.toList());

        if (rutas.isEmpty()) {
            throw new RuntimeException("No se encontró ruta para el repartidor en la fecha especificada");
        }

        return rutas.get(0);
    }

    private int getYear(Date date) {
        return date.getYear() + 1900;
    }

    private int getMonth(Date date) {
        return date.getMonth();
    }

    private int getDate(Date date) {
        return date.getDate();
    }

}
