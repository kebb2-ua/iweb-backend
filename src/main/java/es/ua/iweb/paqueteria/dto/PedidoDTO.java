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
public class PedidoDTO {
    private Integer id;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private DireccionDTO origen;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private DireccionDTO destino;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotEmpty(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private List<@Valid BultoDTO> bultos;

    private String observaciones;
}
