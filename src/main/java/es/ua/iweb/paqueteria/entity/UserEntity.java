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
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nif;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private String razonSocial;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionEntity direccion;

    @OneToOne(cascade = CascadeType.ALL)
    private ZonaEntity zona;

    @Convert(converter = StringListConverter.class)
    @Enumerated(EnumType.STRING)
    private List<RoleType> rolesList;

    @Column
    private String verificationToken;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private AccountStatusType accountStatusType = AccountStatusType.NOT_VERIFIED;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SessionEntity> sessions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
        for (RoleType role : rolesList) {
            authoritiesList.add(new SimpleGrantedAuthority(role.name()));
        }
        return authoritiesList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

}