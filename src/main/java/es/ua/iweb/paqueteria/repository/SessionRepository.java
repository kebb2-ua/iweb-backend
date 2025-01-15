package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.SessionEntity;
import es.ua.iweb.paqueteria.entity.UserEntity;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {

    Optional<SessionEntity> findByAccessToken(String token);

    Optional<SessionEntity> findByAccessTokenAndRefreshTokenAndPublicId(String accessToken, String refreshToken, String publicId);

    List<SessionEntity> findByUserAndIsApiKeyTrue(UserEntity user);

    @Modifying
    @Transactional
    void deleteByPublicId(String publicId);
}