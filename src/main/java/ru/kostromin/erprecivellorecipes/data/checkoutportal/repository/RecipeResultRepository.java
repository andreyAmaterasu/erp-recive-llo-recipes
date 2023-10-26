package ru.kostromin.erprecivellorecipes.data.checkoutportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;

/**
 * Репозиторий для взаимодействия с представлением журнала переданных рецептов
 */
@Repository
public interface RecipeResultRepository extends JpaRepository<RecipeResult, Integer> {

}
