package es.ua.iweb.paqueteria.dto;

import es.ua.iweb.paqueteria.constants.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BultoDTO {
    private Integer id;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String descripcion;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Float peso;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Float altura;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Float ancho;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Float profundidad;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private Boolean peligroso;
}
