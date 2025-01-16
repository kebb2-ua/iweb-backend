package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.constants.ErrorMessages;
import es.ua.iweb.paqueteria.type.EstadoType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstadoPedidoRequest {

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private EstadoType estado;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private LocalDateTime estado_ultima_actualizacion;
}
