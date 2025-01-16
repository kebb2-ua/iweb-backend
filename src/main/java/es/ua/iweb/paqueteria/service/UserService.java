package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.dto.UserDTO;
import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.repository.UserRepository;
import es.ua.iweb.paqueteria.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::buildFromEntity).toList();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(DataNotFoundException::userNotFound);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(DataNotFoundException::userNotFound);
    }

    public void updateUserRole(String email, RoleType role) {
        UserEntity user = getUserByEmail(email);

        // Reemplaza todos los roles existentes por el nuevo rol.
        user.getRolesList().clear();
        user.getRolesList().add(role);

        userRepository.save(user);
    }
}
