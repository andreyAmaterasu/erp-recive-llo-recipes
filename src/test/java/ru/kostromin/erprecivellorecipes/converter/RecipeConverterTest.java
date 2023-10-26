package ru.kostromin.erprecivellorecipes.converter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.kostromin.erprecivellorecipes.config.TransferRecipeAppConfig;
import ru.kostromin.erprecivellorecipes.converter.mapper.RecipeMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Organization;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Prescription;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Privilege;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Smnn;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Subdivision;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Validity;
import ru.kostromin.erprecivellorecipes.data.erp.repository.MedicalWorkerRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.OrganizationRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.PrivilegeRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.SmnnRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.SubdivisionRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.ValidityRepository;

@TestInstance(Lifecycle.PER_METHOD)
class RecipeConverterTest {

  @Mock
  private OrganizationRepository organizationRepository;
  @Mock
  private MedicalWorkerRepository medicalWorkerRepository;
  @Mock
  private SmnnRepository smnnRepository;
  @Mock
  private PrivilegeRepository privilegeRepository;
  @Mock
  private ValidityRepository validityRepository;
  @Mock
  private SubdivisionRepository subdivisionRepository;
  @Mock
  private TransferRecipeAppConfig transferRecipeAppConfig;

  @InjectMocks
  private final RecipeMapper mapper = Mappers.getMapper(RecipeMapper.class);

  private RecipeConverter converter;

  AutoCloseable mocks;

  @BeforeEach
  void setup() {
    mocks = MockitoAnnotations.openMocks(this);
    converter = new RecipeConverter(mapper);
  }

  @Test
  @DisplayName("Рецепт для e-Rp успешно создан из рецепта портала выписки")
  void allFieldsOfRecipeIsFilled() {

    Mockito.when(organizationRepository.findByFederalOid(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(organizationMock()));
    Mockito.when(medicalWorkerRepository.findBySnils(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(getMedicalWorkerMock()));
    Mockito.when(smnnRepository.findByCode(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(getSmnnMock()));
    Mockito.when(privilegeRepository.findByCode(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(getPrivilegeMock()));
    Mockito.when(validityRepository.findByCode(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(getValidityMock()));
    Mockito.when(subdivisionRepository.findByFederalOid(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(getSubdivisionsMock()));
    Mockito.when(transferRecipeAppConfig.getPrescriptionType())
        .thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource())
        .thenReturn(getFundingSourceMock());

    Prescription actualPrescription = converter.convertG(getRecipesViewMock(), Prescription.class);
    final Prescription expectedPrescription = getPrescriptionMock();
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.getIsSended());
  }

  private Prescription getPrescriptionMock() {

    return Prescription.builder()
        .guid(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        .prescribedMedicationGuid(UUID.fromString("00000000-0000-0000-0000-000000000003"))
        .series("series")
        .number("number")
        .datePrescription(LocalDate.of(2023, 1, 1))
        .dateEnd(LocalDate.of(2023, 1, 2))
        .formTypeId(3)
        .isPaper(Boolean.TRUE)
        .patientSnils("patientSnils")
        .patientSurname("patientSurname")
        .patientName("patientName")
        .patientPatronymic("patientPatronymic")
        .patientBirthday(LocalDate.of(2023, 1, 3))
        .genderId((byte) 1)
        .patientLocalId("00000000-0000-0000-0000-000000000000")
        .organizationId(2)
        .doctorId(3)
        .prescriptionName("prescriptionName")
        .prescriptionTypeId(1)
        .isTrn(Boolean.TRUE)
        .signa("signa")
        .numberDose(5.0)
        .smnnId(6)
        .okatoCode("okatoCode")
        .prescriptionStateId(0)
        .schemeUid(UUID.fromString("00000000-0000-0000-0000-000000000004"))
        .mkbCode("mkbCode")
        .softwareName("softwareName")
        .fundingSourceId(1)
        .percentageOfPayment(8)
        .privilegeId(9)
        .validityId(10)
        .subdivisionId(11)
        .dosage("dosage")
        .dosageForm("dosageForm")
        .mnn("mnn")
        .isSended(Boolean.TRUE)
        .build();
  }

  private Recipe getRecipesViewMock() {

    return Recipe.builder()
        .uid(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        .prescribedMedicationGuid(UUID.fromString("00000000-0000-0000-0000-000000000003"))
        .series("series")
        .number("number")
        .dateCreate(LocalDate.of(2023, 1, 1))
        .dateEnd(LocalDate.of(2023, 1, 2))
        .isPaper(Boolean.TRUE)
        .snils("patientSnils")
        .surname("patientSurname")
        .name("patientName")
        .patronymic("patientPatronymic")
        .birthDate(LocalDate.of(2023, 1, 3))
        .gender((byte) 1)
        .localUid(UUID.fromString("00000000-0000-0000-0000-000000000000"))
        .localId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
        .organizationOid("organizationOid")
        .doctorSurname("doctorSurname")
        .doctorName("doctorName")
        .doctorPatronymic("doctorPatronymic")
        .doctorBirthDate(LocalDate.of(2023, 1, 5))
        .doctorSnils("doctorSnils")
        .positionCode("positionCode")
        .specialityCode("specialityCode")
        .prescriptionName("prescriptionName")
        .type("nutrition")
        .isTrn(Boolean.TRUE)
        .signa("signa")
        .numberDose(5.0)
        .code("code")
        .okatoCode("okatoCode")
        .schemeUid(UUID.fromString("00000000-0000-0000-0000-000000000004"))
        .mkbCode("mkbCode")
        .softwareName("softwareName")
        .fundingSourceCode(2)
        .percentageOfPayment("8")
        .privilegeCode("privilegeCode")
        .validityCode("validityCode")
        .subdivisionOid("subdivisionOid")
        .dosage("dosage")
        .dosageForm("dosageForm")
        .mnn("mnn")
        .build();
  }

  private MedicalWorker getMedicalWorkerMock() {

    return MedicalWorker.builder()
        .id(3)
        .localId("00000000-0000-0000-0000-000000000001")
        .surname("doctorSurname")
        .name("doctorName")
        .patronymic("doctorPatronymic")
        .birthDate(LocalDate.of(2023, 1, 5))
        .snils("doctorSnils")
        .positionCode("positionCode")
        .specialityCode("specialityCode")
        .medicalWorkerTypeId(1)
        .organizationId(2)
        .build();
  }

  private Organization organizationMock() {

    return Organization.builder()
        .id(2)
        .federalOid("federalOid")
        .build();
  }

  private Smnn getSmnnMock() {

    return Smnn.builder()
        .id(6)
        .code("code")
        .build();
  }

  private Privilege getPrivilegeMock() {

    return Privilege.builder()
        .id(9)
        .code("privilegeCode")
        .build();
  }

  private Validity getValidityMock() {

    return Validity.builder()
        .id(10)
        .code("validityCode")
        .build();
  }

  private Subdivision getSubdivisionsMock() {

    return Subdivision.builder()
        .id(11)
        .federalOid("subdivisionOid")
        .build();
  }

  private Map<Integer, Integer> getFundingSourceMock() {
    return Map.of(1, 0, 2, 1);
  }

  private Map<String, Integer> getPrescriptionTypeMock() {
    return Map.of("Drug", 0, "nutrition", 1, "device", 2);
  }
}
