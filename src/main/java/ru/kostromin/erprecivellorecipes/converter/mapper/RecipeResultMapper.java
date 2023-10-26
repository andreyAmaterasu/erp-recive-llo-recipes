package ru.kostromin.erprecivellorecipes.converter.mapper;

import java.time.LocalDateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;

/**
 * Маппер {@link Recipe} в {@link RecipeResult}
 */
@Mapper(componentModel = ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public abstract class RecipeResultMapper {

  @Mapping(target = "recipeGUID", source = "uid")
  public abstract RecipeResult recipesResultFromRecipesView(Recipe recipe);

  @AfterMapping
  protected void updateFields(@MappingTarget RecipeResult target) {

    target.setSendDate(LocalDateTime.now());
    target.setIsSend(Boolean.TRUE);
  }
}