package es.ua.iweb.paqueteria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "facturas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Integer numFactura;
    private Date fechaEmision;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private UserEntity emisor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lineaDireccion1", column = @Column(name = "emisor_linea_direccion1")),
            @AttributeOverride(name = "lineaDireccion2", column = @Column(name = "emisor_linea_direccion2")),
            @AttributeOverride(name = "codigoPostal", column = @Column(name = "emisor_codigo_postal")),
            @AttributeOverride(name = "pais", column = @Column(name = "emisor_pais")),
            @AttributeOverride(name = "provincia", column = @Column(name = "emisor_provincia")),
            @AttributeOverride(name = "municipio", column = @Column(name = "emisor_municipio")),
            @AttributeOverride(name = "localidad", column = @Column(name = "emisor_localidad"))
    })
    private DireccionValue emisorDireccion;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private UserEntity receptor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lineaDireccion1", column = @Column(name = "receptor_linea_direccion1")),
            @AttributeOverride(name = "lineaDireccion2", column = @Column(name = "receptor_linea_direccion2")),
            @AttributeOverride(name = "codigoPostal", column = @Column(name = "receptor_codigo_postal")),
            @AttributeOverride(name = "pais", column = @Column(name = "receptor_pais")),
            @AttributeOverride(name = "provincia", column = @Column(name = "receptor_provincia")),
            @AttributeOverride(name = "municipio", column = @Column(name = "receptor_municipio")),
            @AttributeOverride(name = "localidad", column = @Column(name = "receptor_localidad"))
    })
    private DireccionValue receptorDireccion;

    @ManyToOne
    @JoinColumn(name = "albaran_id")
    private AlbaranEntity albaran;

    // @ManyToOne
    // @JoinColumn(name = "pedido_id")
    // private PedidoEntity pedido;
}
