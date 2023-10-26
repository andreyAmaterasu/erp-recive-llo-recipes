package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;

/**
 * Репозиторий для взаимодействия с представлением медицинского работника
 */
@Repository
public interface MedicalWorkerRepository extends JpaRepository<MedicalWorker, Integer> {

  Optional<MedicalWorker> findBySnils(String snils);

  Boolean existsByLocalId(String localId);
}
