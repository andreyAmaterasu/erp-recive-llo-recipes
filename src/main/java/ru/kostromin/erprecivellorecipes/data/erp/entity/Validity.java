package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Представление сроков действия рецепта
 */
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "erp_validity")
public class Validity extends BaseEntity {

  private String code;
}
