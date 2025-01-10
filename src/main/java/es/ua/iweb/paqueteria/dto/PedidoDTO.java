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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoDTO {
    private Integer id;
    private UserDTO repartidor;
    private UserDTO remitente;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private DireccionDTO origen;

    @NotNull(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    @Valid
    private DireccionDTO destino;

    private EstadoType estado;

    private LocalDateTime estado_ultima_actualizacion;

    private PedidoDTO pedido_devolucion;

    private float precio;

    @NotEmpty(message = ErrorMessages.FIELD_CANNOT_BE_BLANK)
    private List<@Valid BultoDTO> bultos;

    private String observaciones;

    private RutaDTO ruta;
}
