package es.ua.iweb.paqueteria.dto;

import es.ua.iweb.paqueteria.type.EstadoType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EstadoPedidoDTO {
    private EstadoType estado;
    private LocalDateTime estado_ultima_actualizacion;
}
