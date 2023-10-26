package ru.kostromin.erprecivellorecipes.converter;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.kostromin.erprecivellorecipes.converter.mapper.MedicalWorkerMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.hostco.dvs.springstarter.converter.pattern.impl.BaseConverterPattern;

/**
 * Конвертер из {@link Recipe} в {@link MedicalWorker}
 */
@Service
public class MedicalWorkerConverter extends BaseConverterPattern<Recipe, MedicalWorker> {

  public MedicalWorkerConverter(@NonNull MedicalWorkerMapper mapper) {

    super(mapper::medicalWorkerFromRecipesView, medicalWorker -> null);
  }
}
