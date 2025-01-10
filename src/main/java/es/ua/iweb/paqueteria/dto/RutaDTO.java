package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.constants.ErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RutaDTO {
    private Integer id;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private UserDTO repartidor;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Date fecha;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotEmpty(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private List<@Valid PedidoRequest> pedidos;
}
