package es.ua.iweb.paqueteria.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Embeddable
public class DireccionCopy {

    private String lineaDireccion1;
    private String lineaDireccion2;
    private String codigoPostal;
    private String pais;
    private String provincia;
    private String municipio;
    private String localidad;
}
