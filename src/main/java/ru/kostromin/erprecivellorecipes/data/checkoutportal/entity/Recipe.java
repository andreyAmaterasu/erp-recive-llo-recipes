package ru.kostromin.erprecivellorecipes.data.checkoutportal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

/**
 * Представление рецепта
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"uid"})
@Entity(name = "V_hst_ERPRecipes")
public class Recipe {

  @Type(type="org.hibernate.type.UUIDCharType")
  @Id
  private UUID uid;

  @Type(type="org.hibernate.type.UUIDCharType")
  private UUID prescribedMedicationGuid;

  private String series;

  private String number;

  private LocalDate dateCreate;

  private LocalDate dateEnd;

  private Boolean isPaper;

  private String snils;

  private String surname;

  private String name;

  private String patronymic;

  @Column(name = "birthday")
  private LocalDate birthDate;

  private Byte gender;

  @Type(type="org.hibernate.type.UUIDCharType")
  private UUID localUid;

  @Type(type="org.hibernate.type.UUIDCharType")
  private UUID localId;

  @Column(name = "organization_oid")
  private String organizationOid;

  @Column(name = "doctor_surname")
  private String doctorSurname;

  @Column(name = "doctor_name")
  private String doctorName;

  @Column(name = "doctor_patronymic")
  private String doctorPatronymic;

  @Column(name = "birthDate")
  private LocalDate doctorBirthDate;

  @Column(name = "doctor_snils")
  private String doctorSnils;

  private String positionCode;

  private String specialityCode;

  private String prescriptionName;

  private String type;

  private Boolean isTrn;

  private String signa;

  private Double numberDose;

  private String code;

  private String okatoCode;

  @Type(type="org.hibernate.type.UUIDCharType")
  private UUID schemeUid;

  private String mkbCode;

  private String softwareName;

  private Integer fundingSourceCode;

  private String percentageOfPayment;

  private String privilegeCode;

  private String validityCode;

  @Column(name = "subdivision_oid")
  private String subdivisionOid;

  private String dosage;

  private String dosageForm;

  private String mnn;
}