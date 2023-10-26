package ru.kostromin.erprecivellorecipes.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Пользовательские настройки сервиса
 */
@Profile("transferRecipe")
@Component
@Data
@ConfigurationProperties(prefix = "app.transfer-recipe")
public class TransferRecipeAppConfig {

  /**
   * Количество выгружаемых рецептов
   */
  private Integer recipeFetchLimit;

  /**
   * Сопоставление типа назначения
   */
  private Map<String, Integer> prescriptionType;

  /**
   * Сопоставление кода источника финансирования
   */
  private Map<Integer, Integer> fundingSource;
}
