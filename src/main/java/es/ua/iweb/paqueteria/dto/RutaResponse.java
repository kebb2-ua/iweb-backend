package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RutaResponse {
    private Integer id;
    private UserDTO repartidor;
    private Date fecha;
    private List<PedidoRequest> pedidos;
}
