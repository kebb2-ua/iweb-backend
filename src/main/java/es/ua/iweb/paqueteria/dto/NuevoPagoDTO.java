package es.ua.iweb.paqueteria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NuevoPagoDTO {
    private float amount;
    private String currency;
    private String description;
    private String reference;
    private String url_callback;
}
