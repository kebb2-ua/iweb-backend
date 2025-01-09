package es.ua.iweb.paqueteria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.ua.iweb.paqueteria.dto.PedidoDTO;
import es.ua.iweb.paqueteria.type.EstadoType;
import es.ua.iweb.paqueteria.StringListConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity repartidor;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity remitente;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lineaDireccion1", column = @Column(name = "origen_linea_direccion1")),
            @AttributeOverride(name = "lineaDireccion2", column = @Column(name = "origen_linea_direccion2")),
            @AttributeOverride(name = "codigoPostal", column = @Column(name = "origen_codigo_postal")),
            @AttributeOverride(name = "pais", column = @Column(name = "origen_pais")),
            @AttributeOverride(name = "provincia", column = @Column(name = "origen_provincia")),
            @AttributeOverride(name = "municipio", column = @Column(name = "origen_municipio")),
            @AttributeOverride(name = "localidad", column = @Column(name = "origen_localidad"))
    })
    private DireccionValue origen;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lineaDireccion1", column = @Column(name = "destino_linea_direccion1")),
            @AttributeOverride(name = "lineaDireccion2", column = @Column(name = "destino_linea_direccion2")),
            @AttributeOverride(name = "codigoPostal", column = @Column(name = "destino_codigo_postal")),
            @AttributeOverride(name = "pais", column = @Column(name = "destino_pais")),
            @AttributeOverride(name = "provincia", column = @Column(name = "destino_provincia")),
            @AttributeOverride(name = "municipio", column = @Column(name = "destino_municipio")),
            @AttributeOverride(name = "localidad", column = @Column(name = "destino_localidad"))
    })
    private DireccionValue destino;

    @Convert(converter = StringListConverter.class)
    @Enumerated(EnumType.STRING)
    private EstadoType estado;

    @Column
    private Date estado_ultima_actualizacion;

    @ManyToOne(cascade = CascadeType.ALL)
    private PedidoEntity pedido_devolucion;

    @Column(nullable = false)
    private float precio;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BultoEntity> bultos = new ArrayList<>();

    @Column
    private String observaciones;

    // Relación con RutaEntity
    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = false)
    @JsonBackReference
    private RutaEntity ruta;

    public PedidoDTO toDTO() {
        return PedidoDTO.builder()
                .id(this.id)
                .repartidor(this.repartidor.toDTO())
                .remitente(this.remitente.toDTO())
                .origen(this.origen)
                .destino(this.destino)
                .estado(this.estado)
                .estado_ultima_actualizacion(this.estado_ultima_actualizacion)
                .pedido_devolucion(this.pedido_devolucion.toDTO())
                .precio(this.precio)
                .bultos(this.bultos.stream().map(BultoEntity::toDTO).toList())
                .observaciones(this.observaciones)
                .ruta(this.ruta)
                .build();
    }
}
