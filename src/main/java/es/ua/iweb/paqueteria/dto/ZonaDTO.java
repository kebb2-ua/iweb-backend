package es.ua.iweb.paqueteria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZonaDTO {
    private Integer id;
    private String nombre;
    private UserDTO user;
}
