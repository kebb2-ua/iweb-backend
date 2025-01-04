package es.ua.iweb.paqueteria.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "rutas")
public class RutaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity repartidor;

    @Column(nullable = false)
    private Date fecha;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PedidoEntity> pedidos = new ArrayList<>();
}
