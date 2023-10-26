package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Subdivision;

/**
 * Репозиторий для взаимодействия с представлением подразделения
 */
@Repository
public interface SubdivisionRepository extends JpaRepository<Subdivision, Integer> {

  Optional<Subdivision> findByFederalOid(String federalOid);
}
