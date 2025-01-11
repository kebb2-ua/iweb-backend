package es.ua.iweb.paqueteria.entity;

import es.ua.iweb.paqueteria.dto.ZonaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "zonas")
public class ZonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @OneToOne(mappedBy = "zona")
    private UserEntity user;

    public ZonaDTO toDTO() {
        return ZonaDTO.builder()
                .id(this.id)
                .nombre(this.nombre)
                .build();
    }
}