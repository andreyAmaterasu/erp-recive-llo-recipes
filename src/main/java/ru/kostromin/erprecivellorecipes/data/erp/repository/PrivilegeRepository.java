package ru.kostromin.erprecivellorecipes.data.erp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Privilege;

/**
 * Репозиторий для взаимодействия с представлением льготы
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

  Optional<Privilege> findByCode(String code);
}
