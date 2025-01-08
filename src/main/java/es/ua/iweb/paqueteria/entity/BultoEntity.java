package es.ua.iweb.paqueteria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "bultos")
public class BultoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private float peso;

    @Column(nullable = false)
    private float altura;

    @Column(nullable = false)
    private float ancho;

    @Column(nullable = false)
    private float profundidad;

    @Column(nullable = false)
    private boolean peligroso;

    // Relación con PedidoEntity
    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference
    private PedidoEntity pedido;
}
