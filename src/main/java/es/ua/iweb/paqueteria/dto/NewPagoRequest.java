package es.ua.iweb.paqueteria.dto;

import es.ua.iweb.paqueteria.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewPagoRequest {
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String seguimiento;
}
