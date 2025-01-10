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
public class DireccionDTO {
    private Integer id;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String nombre;

    private String nif;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String lineaDireccion1;

    private String lineaDireccion2;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String codigoPostal;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String pais;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String provincia;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String municipio;

    @NotBlank(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private String localidad;
}
