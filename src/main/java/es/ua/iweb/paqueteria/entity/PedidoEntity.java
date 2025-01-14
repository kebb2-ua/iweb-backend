package es.ua.iweb.paqueteria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.ua.iweb.paqueteria.dto.PedidoRequest;
import es.ua.iweb.paqueteria.dto.PedidoResponse;
import es.ua.iweb.paqueteria.type.EstadoType;
import es.ua.iweb.paqueteria.StringListConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            @AttributeOverride(name = "nombre", column = @Column(name = "origen_nombre")),
            @AttributeOverride(name = "nif", column = @Column(name = "origen_nif")),
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
            @AttributeOverride(name = "nombre", column = @Column(name = "destino_nombre")),
            @AttributeOverride(name = "nif", column = @Column(name = "destino_nif")),
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
    private String seguimiento;

    @Column
    private LocalDateTime estado_ultima_actualizacion;

    @ManyToOne(cascade = CascadeType.ALL)
    private PedidoEntity pedido_devolucion;

    @Column(nullable = false)
    private float precio;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<BultoEntity> bultos = new ArrayList<>();

    @Column
    private String observaciones;

    // Relación con RutaEntity
    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = true)
    @JsonBackReference
    private RutaEntity ruta;

    public PedidoResponse toDTO() {
        return PedidoResponse.builder()
                .origen(this.origen != null ? this.origen.toDTO() : null)
                .destino(this.destino != null ? this.destino.toDTO() : null)
                .bultos(this.bultos.stream().map(BultoEntity::toDTO).toList())
                .seguimiento(this.seguimiento)
                .observaciones(this.observaciones)
                .build();
    }
}
