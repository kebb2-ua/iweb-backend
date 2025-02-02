package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByVerificationToken(String token);

    Optional<UserEntity> findByNif(String token);
}
