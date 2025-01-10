package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.constants.ErrorMessages;
import es.ua.iweb.paqueteria.type.EstadoType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EstadoPedidoDTO {
    private EstadoType estado;
    private LocalDateTime estado_ultima_actualizacion;
}
