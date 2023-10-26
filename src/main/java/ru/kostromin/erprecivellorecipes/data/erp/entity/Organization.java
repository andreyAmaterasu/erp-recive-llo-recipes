package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Представление организации
 */
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "organizations")
public class Organization extends BaseEntity {

  @Column(name = "federal_oid")
  private String federalOid;
}
