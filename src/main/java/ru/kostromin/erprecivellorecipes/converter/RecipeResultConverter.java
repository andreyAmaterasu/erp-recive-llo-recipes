package ru.kostromin.erprecivellorecipes.converter;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.kostromin.erprecivellorecipes.converter.mapper.RecipeResultMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.hostco.dvs.springstarter.converter.pattern.impl.BaseConverterPattern;

/**
 * Конвертер из {@link Recipe} в {@link RecipeResult}
 */
@Service
public class RecipeResultConverter extends BaseConverterPattern<Recipe, RecipeResult> {

  public RecipeResultConverter(@NonNull RecipeResultMapper mapper) {

    super(mapper::recipesResultFromRecipesView, recipesResult -> null);
  }
}
