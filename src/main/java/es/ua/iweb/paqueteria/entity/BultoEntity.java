package es.ua.iweb.paqueteria.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.ua.iweb.paqueteria.StringListConverter;
import es.ua.iweb.paqueteria.type.AccountStatusType;
import es.ua.iweb.paqueteria.type.RoleType;
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

    @Column
    private float peso;

    @Column
    private float altura;

    @Column
    private float ancho;

    @Column
    private float profundidad;

    @Column
    private boolean peligroso;
}
