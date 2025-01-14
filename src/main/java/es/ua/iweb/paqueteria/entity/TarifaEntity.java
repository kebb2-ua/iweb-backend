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

    @Column
    private Float precioMinimo;

    @Column
    private Float precioBase;

    @Column
    private Float peso;

    @Column
    private Float peligroso;
}
