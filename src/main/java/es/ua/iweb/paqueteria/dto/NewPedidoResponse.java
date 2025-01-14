package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewPedidoResponse {
    private Integer id_envio;
    private LocalDateTime fecha_creacion;
}
