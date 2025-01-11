package es.ua.iweb.paqueteria.service;

import es.ua.iweb.paqueteria.entity.UserEntity;
import es.ua.iweb.paqueteria.exception.DataNotFoundException;
import es.ua.iweb.paqueteria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(DataNotFoundException::userNotFound);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(DataNotFoundException::userNotFound);
    }
}
