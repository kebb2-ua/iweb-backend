package es.ua.iweb.paqueteria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BultoDTO {
    private Integer id;
    private String descripcion;
    private float peso;
    private float altura;
    private float ancho;
    private float profundidad;
    private boolean peligroso;
}
