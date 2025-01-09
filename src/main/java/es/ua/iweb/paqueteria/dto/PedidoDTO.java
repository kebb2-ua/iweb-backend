package es.ua.iweb.paqueteria.dto;

import es.ua.iweb.paqueteria.entity.*;
import es.ua.iweb.paqueteria.type.EstadoType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PedidoDTO {
    private Integer id;
    private UserDTO repartidor;
    private UserDTO remitente;
    private DireccionValue origen;
    private DireccionValue destino;
    private EstadoType estado;
    private Date estado_ultima_actualizacion;
    private PedidoDTO pedido_devolucion;
    private float precio;
    private List<BultoDTO> bultos;
    private String observaciones;
    private RutaEntity ruta;
}
