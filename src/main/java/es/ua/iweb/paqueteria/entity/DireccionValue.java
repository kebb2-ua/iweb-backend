package es.ua.iweb.paqueteria.entity;

import es.ua.iweb.paqueteria.dto.DireccionDTO;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DireccionValue {
    private String nombre;
    private String nif;
    private String lineaDireccion1;
    private String lineaDireccion2;
    private String codigoPostal;
    private String pais;
    private String provincia;
    private String municipio;
    private String localidad;

    public DireccionDTO toDTO() {
        return DireccionDTO.builder()
                .nombre(this.nombre)
                .nif(this.nif)
                .lineaDireccion1(this.lineaDireccion1)
                .lineaDireccion2(this.lineaDireccion2)
                .codigoPostal(this.codigoPostal)
                .pais(this.pais)
                .provincia(this.provincia)
                .municipio(this.municipio)
                .localidad(this.localidad)
                .build();
    }

    public static DireccionValue buildFromDTO(DireccionDTO direccionDTO) {
        return DireccionValue.builder()
                .nombre(direccionDTO.getNombre())
                .nif(direccionDTO.getNif())
                .lineaDireccion1(direccionDTO.getLineaDireccion1())
                .lineaDireccion2(direccionDTO.getLineaDireccion2())
                .codigoPostal(direccionDTO.getCodigoPostal())
                .pais(direccionDTO.getPais())
                .provincia(direccionDTO.getProvincia())
                .municipio(direccionDTO.getMunicipio())
                .localidad(direccionDTO.getLocalidad())
                .build();
    }
}
