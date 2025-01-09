package es.ua.iweb.paqueteria.dto;

import es.ua.iweb.paqueteria.entity.DireccionValue;
import es.ua.iweb.paqueteria.type.AccountStatusType;
import es.ua.iweb.paqueteria.type.RoleType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String nif;
    private String nombre;
    private String apellidos;
    private String razonSocial;
    private String email;
    private DireccionValue direccion;
    private ZonaDTO zona;
    private List<RoleType> rolesList;
    private AccountStatusType accountStatusType;
}
