package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Prescription;

/**
 * Репозиторий для взаимодействия с представлением рецепта
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

  Boolean existsByGuid(UUID guid);
}
