package es.ua.iweb.paqueteria.entity;

import es.ua.iweb.paqueteria.StringListConverter;
import es.ua.iweb.paqueteria.type.EstadoType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@SuperBuilder
@Table(name = "pagos")
public class PagoEntity extends DatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(mappedBy = "pago")
    private PedidoEntity pedido;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false, unique = true)
    private String urlPasarela;

    @Convert(converter = StringListConverter.class)
    @Enumerated(EnumType.STRING)
    private EstadoType estado = EstadoType.PENDIENTE;

}
