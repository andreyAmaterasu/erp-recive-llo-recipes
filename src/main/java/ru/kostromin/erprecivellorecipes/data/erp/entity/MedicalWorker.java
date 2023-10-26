package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Представление медицинского работника
 */
@ToString
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "erp_medical_worker")
public class MedicalWorker extends BaseEntity {

  @Column(name = "local_id")
  private String localId;

  private String surname;

  private String name;

  private String patronymic;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  private String snils;

  @Column(name = "position_code")
  private String positionCode;

  @Column(name = "speciality_code")
  private String specialityCode;

  @Column(name = "medical_worker_type_id")
  private Integer medicalWorkerTypeId;

  @Column(name = "organization_id")
  private Integer organizationId;
}
