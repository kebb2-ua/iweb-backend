package es.ua.iweb.paqueteria.entity;

import es.ua.iweb.paqueteria.dto.DireccionDTO;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "direcciones")
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String lineaDireccion1;

    @Column
    private String lineaDireccion2;

    @Column(nullable = false)
    private String codigoPostal;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String provincia;

    @Column(nullable = false)
    private String municipio;

    @Column(nullable = false)
    private String localidad;

    @OneToOne(mappedBy = "direccion")
    private UserEntity user;

    public DireccionValue toEmbeddable() {
        return DireccionValue.builder()
                .lineaDireccion1(lineaDireccion1)
                .lineaDireccion2(lineaDireccion2)
                .codigoPostal(codigoPostal)
                .pais(pais)
                .provincia(provincia)
                .municipio(municipio)
                .localidad(localidad)
                .build();
    }

    public DireccionDTO toDTO() {
        return DireccionDTO.builder()
                .lineaDireccion1(lineaDireccion1)
                .lineaDireccion2(lineaDireccion2)
                .codigoPostal(codigoPostal)
                .pais(pais)
                .provincia(provincia)
                .municipio(municipio)
                .localidad(localidad)
                .build();
    }

}