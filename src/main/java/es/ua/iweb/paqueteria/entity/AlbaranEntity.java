package es.ua.iweb.paqueteria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "albaranes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AlbaranEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Integer numAlbaran;
    private Date fechaEmision;
    private Date fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private UserEntity emisor;

    @Embedded
    private DireccionValue emisorDireccion;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private UserEntity receptor;

    @Embedded
    private DireccionValue receptorDireccion;

    // @ManyToOne
    // @JoinColumn(name = "pedido_id")
    // private PedidoEntity pedido;
}
