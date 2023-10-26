package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Validity;

/**
 * Репозиторий для взаимодействия с представлением сроков действия рецепта
 */
@Repository
public interface ValidityRepository extends JpaRepository<Validity, Integer> {

  Optional<Validity> findByCode(String code);
}
