package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.DireccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionEntity, Integer> {
}
