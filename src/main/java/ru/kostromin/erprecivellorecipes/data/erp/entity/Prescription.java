package ru.kostromin.erprecivellorecipes.data.erp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Представление рецепта
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = {"guid"}, callSuper = false)
@Entity(name = "erp_prescription")
public class Prescription extends BaseEntity {

  private UUID guid;

  @Column(name = "prescribed_medication_guid")
  private UUID prescribedMedicationGuid;

  private String series;

  private String number;

  @Column(name = "date_prescription")
  private LocalDate datePrescription;

  @Column(name = "date_end")
  private LocalDate dateEnd;

  @Column(name = "form_type_id")
  private Integer formTypeId;

  @Column(name = "is_paper")
  private Boolean isPaper;

  @Column(name = "patient_snils")
  private String patientSnils;

  @Column(name = "patient_surname")
  private String patientSurname;

  @Column(name = "patient_name")
  private String patientName;

  @Column(name = "patient_patronymic")
  private String patientPatronymic;

  @Column(name = "patient_birthday")
  private LocalDate patientBirthday;

  @Column(name = "gender_id")
  private Byte genderId;

  @Column(name = "patient_local_id")
  private String patientLocalId;

  @Column(name = "organization_id")
  private Integer organizationId;

  @Column(name = "doctor_id")
  private Integer doctorId;

  @Column(name = "prescription_name")
  private String prescriptionName;

  @Column(name = "prescription_type_id")
  private Integer prescriptionTypeId;

  @Column(name = "is_trn")
  private Boolean isTrn;

  private String signa;

  @Column(name = "number_dose")
  private Double numberDose;

  @Column(name = "smnn_id")
  private Integer smnnId;

  @Column(name = "okato_code")
  private String okatoCode;

  @Column(name = "prescription_state_id")
  private Integer prescriptionStateId;

  @Column(name = "scheme_uid")
  private UUID schemeUid;

  @Column(name = "mkb_code")
  private String mkbCode;

  @Column(name = "software_name")
  private String softwareName;

  @Column(name = "funding_source_id")
  private Integer fundingSourceId;

  @Column(name = "percentage_of_payment")
  private Integer percentageOfPayment;

  @Column(name = "privilege_id")
  private Integer privilegeId;

  @Column(name = "validity_id")
  private Integer validityId;

  @Column(name = "subdivision_id")
  private Integer subdivisionId;

  private String dosage;

  @Column(name = "dosage_form")
  private String dosageForm;

  private String mnn;

  @Column(name = "is_sended")
  private Boolean isSended;
}
