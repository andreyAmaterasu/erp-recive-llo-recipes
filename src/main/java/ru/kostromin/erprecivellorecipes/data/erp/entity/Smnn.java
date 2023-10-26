package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Представление СМНН
 */
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "erp_smnn")
public class Smnn extends BaseEntity {

  private String code;
}
