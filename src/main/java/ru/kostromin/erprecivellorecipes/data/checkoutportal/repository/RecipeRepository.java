package ru.kostromin.erprecivellorecipes.data.checkoutportal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;

/**
 * Репозиторий для взаимодействия с представлением рецепта в базе портала выписки
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {

  @Query(value = "select * from V_hst_ERPRecipes order by uid offset 0 rows fetch next :limit rows only",
      nativeQuery = true)
  List<Recipe> findAllByLimit(@Param("limit") Integer limit);
}
