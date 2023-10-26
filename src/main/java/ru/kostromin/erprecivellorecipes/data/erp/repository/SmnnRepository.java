package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Smnn;

/**
 * Репозиторий для взаимодействия с представлением СМНН
 */
@Repository
public interface SmnnRepository extends JpaRepository<Smnn, Integer> {

  Optional<Smnn> findByCode(String code);
}
