package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Представление льготы
 */
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "erp_privilege")
public class Privilege extends BaseEntity {

  private String code;
}
