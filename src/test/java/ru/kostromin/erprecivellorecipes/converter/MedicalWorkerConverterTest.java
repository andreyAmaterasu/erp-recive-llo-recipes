package ru.kostromin.erprecivellorecipes.converter;

import java.time.LocalDate;
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
import ru.kostromin.erprecivellorecipes.converter.mapper.MedicalWorkerMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Organization;
import ru.kostromin.erprecivellorecipes.data.erp.repository.OrganizationRepository;

@TestInstance(Lifecycle.PER_METHOD)
class MedicalWorkerConverterTest {

  @Mock
  private OrganizationRepository organizationRepository;

  @InjectMocks
  private final MedicalWorkerMapper mapper = Mappers.getMapper(MedicalWorkerMapper.class);

  private MedicalWorkerConverter converter;

  AutoCloseable mocks;

  @BeforeEach
  void setup() {
    mocks = MockitoAnnotations.openMocks(this);
    converter = new MedicalWorkerConverter(mapper);
  }

  @Test
  @DisplayName("Все поля в представлении медицинского работника заполнены")
  void allFieldsOfMedicalWorkerIsFilled() {

    Mockito.when(organizationRepository.findByFederalOid(Mockito.anyString()))
        .thenReturn(Optional.ofNullable(organizationMock()));

    MedicalWorker medicalWorkerActual = converter.convertG(getRecipesViewMock(), MedicalWorker.class);
    final MedicalWorker medicalWorkerExpected = getMedicalWorkerMock();
    Assertions.assertEquals(medicalWorkerActual.getLocalId(),
        medicalWorkerExpected.getLocalId());
    Assertions.assertEquals(medicalWorkerActual.getSurname(),
        medicalWorkerExpected.getSurname());
    Assertions.assertEquals(medicalWorkerActual.getName(),
        medicalWorkerExpected.getName());
    Assertions.assertEquals(medicalWorkerActual.getPatronymic(),
        medicalWorkerExpected.getPatronymic());
    Assertions.assertEquals(medicalWorkerActual.getBirthDate(),
        medicalWorkerExpected.getBirthDate());
    Assertions.assertEquals(medicalWorkerActual.getSnils(),
        medicalWorkerExpected.getSnils());
    Assertions.assertEquals(medicalWorkerActual.getPositionCode(),
        medicalWorkerExpected.getPositionCode());
    Assertions.assertEquals(medicalWorkerActual.getSpecialityCode(),
        medicalWorkerExpected.getSpecialityCode());
    Assertions.assertEquals(medicalWorkerActual.getMedicalWorkerTypeId(),
        medicalWorkerExpected.getMedicalWorkerTypeId());
    Assertions.assertEquals(medicalWorkerActual.getOrganizationId(),
        medicalWorkerExpected.getOrganizationId());
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
}
