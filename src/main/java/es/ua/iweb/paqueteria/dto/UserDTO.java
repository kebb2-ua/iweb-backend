package es.ua.iweb.paqueteria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.type.AccountStatusType;
import es.ua.iweb.paqueteria.type.RoleType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer id;
    private String nif;
    private String nombre;
    private String apellidos;
    private String razonSocial;
    private String email;
    private DireccionDTO direccion;
    private ZonaDTO zona;
    private List<RoleType> rolesList;
    private AccountStatusType accountStatusType;

    public static UserDTO buildFromEntity(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .nif(userEntity.getNif())
                .nombre(userEntity.getNombre())
                .apellidos(userEntity.getApellidos())
                .razonSocial(userEntity.getRazonSocial())
                .email(userEntity.getEmail())
                .direccion(userEntity.getDireccion() != null ? userEntity.getDireccion().toDTO() : null)
                .zona(userEntity.getZona() != null ? userEntity.getZona().toDTO() : null)
                .rolesList(userEntity.getRolesList())
                .accountStatusType(userEntity.getAccountStatusType())
                .build();
    }
}
