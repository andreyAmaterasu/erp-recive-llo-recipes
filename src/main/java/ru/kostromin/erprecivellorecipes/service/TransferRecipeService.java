package ru.kostromin.erprecivellorecipes.service;

import io.vavr.control.Try;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.kostromin.erprecivellorecipes.config.TransferRecipeAppConfig;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.repository.RecipeResultRepository;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.repository.RecipeRepository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Prescription;
import ru.kostromin.erprecivellorecipes.data.erp.repository.PrescriptionRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.MedicalWorkerRepository;
import ru.hostco.dvs.springstarter.converter.pool.ConverterPool;

/**
 * Сервисный слой для процесса передачи рецептов из портала выписки в базу e-Rp
 */
@Profile("transferRecipe")
@Slf4j
@Service
@AllArgsConstructor
public class TransferRecipeService implements ScheduledProcess {

  private final RecipeRepository recipeRepository;

  private final RecipeResultRepository recipeResultRepository;

  private final PrescriptionRepository prescriptionRepository;

  private final MedicalWorkerRepository medicalWorkerRepository;

  private final TransferRecipeAppConfig transferRecipeAppConfig;

  private final ConverterPool converterPool;

  /**
   * Метод для поиска рецептов и передачи в базу e-Rp
   */
  public void run() {

    log.info("- TransferRecipe job started. Поиск рецептов для передачи в e-Rp...");
    final Map<UUID, Prescription> prescriptions = new HashMap<>();
    final Map<UUID, RecipeResult> recipeResults = new HashMap<>();
    recipeRepository.findAllByLimit(transferRecipeAppConfig.getRecipeFetchLimit())
        .forEach(recipe -> {
          final String localId = String.valueOf(recipe.getLocalId());
          if (Boolean.FALSE.equals(medicalWorkerRepository.existsByLocalId(localId))) {
            Try.run(() -> medicalWorkerRepository.saveAndFlush(converterPool.convertG(recipe, MedicalWorker.class)))
                .onFailure(e -> log.error("Ошибка при сохранении врача в таблице erp_medical_worker", e))
                .onSuccess(r -> log.info("Добавлен новый врач с local_id = {}", localId));
          }

          final UUID recipeUid = recipe.getUid();
          if (Boolean.FALSE.equals(prescriptionRepository.existsByGuid(recipeUid))) {
            prescriptions.putIfAbsent(recipeUid, converterPool.convertG(recipe, Prescription.class));
            recipeResults.putIfAbsent(recipeUid, converterPool.convertG(recipe, RecipeResult.class));
          }
        });

    if (!CollectionUtils.isEmpty(prescriptions)) {
      Try.run(() -> {
        prescriptionRepository.saveAll(prescriptions.values());
        recipeResultRepository.saveAll(recipeResults.values());
      }).onFailure(e ->
              log.error("Ошибка при сохранении данных в таблицу erp_prescription или hst_ERPRecipes", e))
          .onSuccess(r ->
              log.info("Передано рецептов в e-Rp: {}. Переданные рецепты: {}",
                  prescriptions.size(), recipeResults));
    } else {
      log.info("Не найдено ни одного рецепта для передачи в e-Rp");
    }
    log.info("- TransferRecipe job ended");
  }
}