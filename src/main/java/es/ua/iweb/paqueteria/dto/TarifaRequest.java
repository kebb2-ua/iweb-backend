package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarifaRequest {
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String codigo_postal;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String peso;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Boolean peligroso;
}
