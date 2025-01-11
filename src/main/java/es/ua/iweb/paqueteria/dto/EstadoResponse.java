package es.ua.iweb.paqueteria.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadoResponse {
    private String estado_actual;
    private Date ultima_actualizacion;
}
