package es.ua.iweb.paqueteria.repository;

import es.ua.iweb.paqueteria.entity.TarifaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<TarifaEntity, Integer> {
    @NonNull Optional<TarifaEntity> findById(@NonNull Integer id);
}
