package es.ua.iweb.paqueteria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarifas")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TarifaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private Float peso;
    private Float distancia;
    private Boolean peligroso;
}
