package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Organization;

/**
 * Репозиторий для взаимодействия с представлением организации
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

  Optional<Organization> findByFederalOid(String federalOid);
}
