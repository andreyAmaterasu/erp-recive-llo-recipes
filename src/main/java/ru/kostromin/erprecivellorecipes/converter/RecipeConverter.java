package ru.kostromin.erprecivellorecipes.converter;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.kostromin.erprecivellorecipes.converter.mapper.RecipeMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Prescription;
import ru.hostco.dvs.springstarter.converter.pattern.impl.BaseConverterPattern;

/**
 * Конвертер из {@link Recipe} в {@link Prescription}
 */
@Service
public class RecipeConverter extends BaseConverterPattern<Recipe, Prescription> {

  public RecipeConverter(@NonNull RecipeMapper mapper) {

    super(mapper::prescriptionFromRecipesView, prescription -> null);
  }
}
