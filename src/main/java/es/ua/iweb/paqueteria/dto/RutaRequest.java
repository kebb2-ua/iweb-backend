package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.constants.ErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RutaRequest {
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Integer repartidorId;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Date fecha;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotEmpty(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private List<Integer> idsPedidos;
}
