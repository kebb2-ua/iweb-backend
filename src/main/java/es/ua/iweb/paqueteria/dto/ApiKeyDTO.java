package es.ua.iweb.paqueteria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiKeyDTO {
    private String publicId;
}
