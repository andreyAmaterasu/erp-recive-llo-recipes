package ru.kostromin.erprecivellorecipes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.kostromin.erprecivellorecipes.config.TransferRecipeAppConfig;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.repository.RecipeResultRepository;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.repository.RecipeRepository;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Prescription;
import ru.kostromin.erprecivellorecipes.data.erp.repository.MedicalWorkerRepository;
import ru.kostromin.erprecivellorecipes.data.erp.repository.PrescriptionRepository;
import ru.hostco.dvs.springstarter.converter.pool.ConverterPool;

@TestInstance(Lifecycle.PER_METHOD)
class TransferRecipeServiceTest {

  @Mock
  private RecipeRepository recipeRepository;
  @Mock
  private RecipeResultRepository recipeResultRepository;
  @Mock
  private PrescriptionRepository prescriptionRepository;
  @Mock
  private MedicalWorkerRepository medicalWorkerRepository;
  @Mock
  private TransferRecipeAppConfig transferRecipeAppConfig;
  @Mock
  private ConverterPool converterPool;

  @InjectMocks
  private TransferRecipeService transferRecipeService;

  AutoCloseable mocks;

  @BeforeEach
  void setup() {
    mocks = MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач найден. "
      + "Данные успешно сохранены в таблицы erp_prescription или hst_ERPRecipes")
  void receptIsFoundDoctorIsFoundDataSavedSuccessful() {

    Mockito.when(recipeRepository.findAllByLimit(Mockito.any()))
        .thenReturn(List.of(getRecipesViewMock()));
    Mockito.when(medicalWorkerRepository.existsByLocalId(Mockito.anyString())).thenReturn(Boolean.TRUE);
    Mockito.when(prescriptionRepository.existsByGuid(Mockito.any())).thenReturn(Boolean.FALSE);
    Mockito.when(transferRecipeAppConfig.getPrescriptionType()).thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource()).thenReturn(getFundingSourceMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(Prescription.class)))
        .thenReturn(getPrescriptionMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(RecipeResult.class)))
        .thenReturn(getRecipeResultMock());

    transferRecipeService.run();

    Mockito.verify(medicalWorkerRepository, Mockito.times(0))
        .saveAll(Mockito.any());

    Prescription expectedPrescription = getPrescriptionMock();
    ArgumentCaptor<Collection<Prescription>> prescriptionCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(prescriptionRepository, Mockito.times(1))
        .saveAll(prescriptionCaptor.capture());
    List<Prescription> actualPrescription = prescriptionCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedPrescription.getId(),
        actualPrescription.get(0).getId());
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.get(0).getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.get(0).getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.get(0).getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.get(0).getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.get(0).getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.get(0).getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.get(0).getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.get(0).getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.get(0).getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.get(0).getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.get(0).getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.get(0).getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.get(0).getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.get(0).getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.get(0).getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.get(0).getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.get(0).getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.get(0).getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.get(0).getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.get(0).getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.get(0).getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.get(0).getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.get(0).getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.get(0).getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.get(0).getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.get(0).getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.get(0).getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.get(0).getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.get(0).getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.get(0).getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.get(0).getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.get(0).getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.get(0).getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.get(0).getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.get(0).getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.get(0).getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.get(0).getIsSended());

    RecipeResult expectedRecipeResults = getRecipeResultMock();
    ArgumentCaptor<Collection<RecipeResult>> recipesResultsCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(recipeResultRepository, Mockito.times(1))
        .saveAll(recipesResultsCaptor.capture());
    List<RecipeResult> actualRecipeResults = recipesResultsCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedRecipeResults.getId(), actualRecipeResults.get(0).getId());
    Assertions.assertEquals(expectedRecipeResults.getRecipeGUID(),
        actualRecipeResults.get(0).getRecipeGUID());
    Assertions.assertEquals(expectedRecipeResults.getIsSend(), actualRecipeResults.get(0).getIsSend());
    Assertions.assertEquals(expectedRecipeResults.getSendDate().toLocalDate(),
        actualRecipeResults.get(0).getSendDate().toLocalDate());
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач не найден. "
      + "Врач создан в таблице erp_medical_worker. "
      + "Данные успешно сохранены в таблицы erp_prescription или hst_ERPRecipes")
  void receptIsFoundDoctorNotFoundDoctorIsCreateDataSavedSuccessful() {

    Mockito.when(recipeRepository.findAllByLimit(Mockito.any()))
        .thenReturn(List.of(getRecipesViewMock()));
    Mockito.when(prescriptionRepository.existsByGuid(Mockito.any())).thenReturn(Boolean.FALSE);
    Mockito.when(medicalWorkerRepository.existsByLocalId(Mockito.anyString())).thenReturn(Boolean.FALSE);
    Mockito.when(transferRecipeAppConfig.getPrescriptionType()).thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource()).thenReturn(getFundingSourceMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(MedicalWorker.class)))
        .thenReturn(getMedicalWorkerMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(Prescription.class)))
        .thenReturn(getPrescriptionMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(RecipeResult.class)))
        .thenReturn(getRecipeResultMock());

    transferRecipeService.run();

    MedicalWorker expectedMedicalWorker = getMedicalWorkerMock();
    ArgumentCaptor<MedicalWorker> medicalWorkerCaptor = ArgumentCaptor.forClass(MedicalWorker.class);
    Mockito.verify(medicalWorkerRepository, Mockito.times(1))
        .saveAndFlush(medicalWorkerCaptor.capture());
    MedicalWorker actualMedicalWorker = medicalWorkerCaptor.getValue();
    Assertions.assertEquals(expectedMedicalWorker.getLocalId(), actualMedicalWorker.getLocalId());
    Assertions.assertEquals(expectedMedicalWorker.getSurname(), actualMedicalWorker.getSurname());
    Assertions.assertEquals(expectedMedicalWorker.getName(), actualMedicalWorker.getName());
    Assertions.assertEquals(expectedMedicalWorker.getPatronymic(), actualMedicalWorker.getPatronymic());
    Assertions.assertEquals(expectedMedicalWorker.getBirthDate(), actualMedicalWorker.getBirthDate());
    Assertions.assertEquals(expectedMedicalWorker.getSnils(), actualMedicalWorker.getSnils());
    Assertions.assertEquals(expectedMedicalWorker.getPositionCode(),
        actualMedicalWorker.getPositionCode());
    Assertions.assertEquals(expectedMedicalWorker.getSpecialityCode(),
        actualMedicalWorker.getSpecialityCode());
    Assertions.assertEquals(expectedMedicalWorker.getMedicalWorkerTypeId(),
        actualMedicalWorker.getMedicalWorkerTypeId());
    Assertions.assertEquals(expectedMedicalWorker.getOrganizationId(),
        actualMedicalWorker.getOrganizationId());

    Prescription expectedPrescription = getPrescriptionMock();
    ArgumentCaptor<Collection<Prescription>> prescriptionCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(prescriptionRepository, Mockito.times(1))
        .saveAll(prescriptionCaptor.capture());
    List<Prescription> actualPrescription = prescriptionCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedPrescription.getId(),
        actualPrescription.get(0).getId());
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.get(0).getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.get(0).getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.get(0).getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.get(0).getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.get(0).getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.get(0).getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.get(0).getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.get(0).getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.get(0).getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.get(0).getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.get(0).getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.get(0).getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.get(0).getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.get(0).getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.get(0).getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.get(0).getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.get(0).getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.get(0).getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.get(0).getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.get(0).getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.get(0).getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.get(0).getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.get(0).getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.get(0).getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.get(0).getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.get(0).getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.get(0).getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.get(0).getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.get(0).getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.get(0).getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.get(0).getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.get(0).getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.get(0).getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.get(0).getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.get(0).getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.get(0).getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.get(0).getIsSended());

    RecipeResult expectedRecipeResults = getRecipeResultMock();
    ArgumentCaptor<Collection<RecipeResult>> recipesResultsCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(recipeResultRepository, Mockito.times(1))
        .saveAll(recipesResultsCaptor.capture());
    List<RecipeResult> actualRecipeResults = recipesResultsCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedRecipeResults.getId(), actualRecipeResults.get(0).getId());
    Assertions.assertEquals(expectedRecipeResults.getRecipeGUID(),
        actualRecipeResults.get(0).getRecipeGUID());
    Assertions.assertEquals(expectedRecipeResults.getIsSend(), actualRecipeResults.get(0).getIsSend());
    Assertions.assertEquals(expectedRecipeResults.getSendDate().toLocalDate(),
        actualRecipeResults.get(0).getSendDate().toLocalDate());
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач не найден. "
      + "Ошибка при создании врача. "
      + "Данные успешно сохранены в таблицы erp_prescription или hst_ERPRecipes")
  void receptIsFoundDoctorNotFoundDoctorCreatedFailureDataSavedSuccessful() {

    Mockito.when(recipeRepository.findAllByLimit(Mockito.any()))
        .thenReturn(List.of(getRecipesViewMock()));
    Mockito.when(prescriptionRepository.existsByGuid(Mockito.any())).thenReturn(Boolean.FALSE);
    Mockito.when(medicalWorkerRepository.existsByLocalId(Mockito.anyString())).thenReturn(Boolean.FALSE);
    Mockito.when(transferRecipeAppConfig.getPrescriptionType()).thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource()).thenReturn(getFundingSourceMock());
    Mockito.when(medicalWorkerRepository.saveAll(Mockito.any()))
        .thenThrow(new RuntimeException("Ошибка при создании врача"));
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(MedicalWorker.class)))
        .thenReturn(getMedicalWorkerMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(Prescription.class)))
        .thenReturn(getPrescriptionMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(RecipeResult.class)))
        .thenReturn(getRecipeResultMock());

    transferRecipeService.run();

    Prescription expectedPrescription = getPrescriptionMock();
    ArgumentCaptor<Collection<Prescription>> prescriptionCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(prescriptionRepository, Mockito.times(1))
        .saveAll(prescriptionCaptor.capture());
    List<Prescription> actualPrescription = prescriptionCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedPrescription.getId(),
        actualPrescription.get(0).getId());
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.get(0).getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.get(0).getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.get(0).getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.get(0).getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.get(0).getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.get(0).getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.get(0).getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.get(0).getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.get(0).getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.get(0).getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.get(0).getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.get(0).getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.get(0).getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.get(0).getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.get(0).getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.get(0).getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.get(0).getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.get(0).getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.get(0).getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.get(0).getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.get(0).getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.get(0).getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.get(0).getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.get(0).getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.get(0).getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.get(0).getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.get(0).getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.get(0).getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.get(0).getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.get(0).getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.get(0).getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.get(0).getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.get(0).getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.get(0).getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.get(0).getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.get(0).getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.get(0).getIsSended());

    RecipeResult expectedRecipeResults = getRecipeResultMock();
    ArgumentCaptor<Collection<RecipeResult>> recipesResultsCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(recipeResultRepository, Mockito.times(1))
        .saveAll(recipesResultsCaptor.capture());
    List<RecipeResult> actualRecipeResults = recipesResultsCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedRecipeResults.getId(), actualRecipeResults.get(0).getId());
    Assertions.assertEquals(expectedRecipeResults.getRecipeGUID(),
        actualRecipeResults.get(0).getRecipeGUID());
    Assertions.assertEquals(expectedRecipeResults.getIsSend(), actualRecipeResults.get(0).getIsSend());
    Assertions.assertEquals(expectedRecipeResults.getSendDate().toLocalDate(),
        actualRecipeResults.get(0).getSendDate().toLocalDate());
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач не найден. "
      + "Врач создан в таблице erp_medical_worker. "
      + "Ошибка при сохранению данных erp_prescription")
  void receptIsFoundDoctorNotFoundDoctorIsCreateDataSavedToErpPrescriptionFailure() {

    Mockito.when(recipeRepository.findAllByLimit(Mockito.any()))
        .thenReturn(List.of(getRecipesViewMock()));
    Mockito.when(prescriptionRepository.existsByGuid(Mockito.any())).thenReturn(Boolean.FALSE);
    Mockito.when(medicalWorkerRepository.existsByLocalId(Mockito.anyString())).thenReturn(Boolean.TRUE);
    Mockito.when(transferRecipeAppConfig.getPrescriptionType()).thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource()).thenReturn(getFundingSourceMock());
    Mockito.when(prescriptionRepository.saveAll(Mockito.any()))
        .thenThrow(new RuntimeException("Ошибка при сохранении в таблицу erp_prescription"));
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(Prescription.class)))
        .thenReturn(getPrescriptionMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(RecipeResult.class)))
        .thenReturn(getRecipeResultMock());

    transferRecipeService.run();

    Prescription expectedPrescription = getPrescriptionMock();
    ArgumentCaptor<Collection<Prescription>> prescriptionCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(prescriptionRepository, Mockito.times(1))
        .saveAll(prescriptionCaptor.capture());
    List<Prescription> actualPrescription = prescriptionCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedPrescription.getId(),
        actualPrescription.get(0).getId());
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.get(0).getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.get(0).getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.get(0).getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.get(0).getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.get(0).getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.get(0).getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.get(0).getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.get(0).getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.get(0).getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.get(0).getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.get(0).getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.get(0).getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.get(0).getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.get(0).getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.get(0).getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.get(0).getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.get(0).getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.get(0).getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.get(0).getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.get(0).getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.get(0).getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.get(0).getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.get(0).getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.get(0).getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.get(0).getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.get(0).getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.get(0).getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.get(0).getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.get(0).getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.get(0).getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.get(0).getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.get(0).getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.get(0).getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.get(0).getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.get(0).getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.get(0).getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.get(0).getIsSended());
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач не найден. "
      + "Врач создан в таблице erp_medical_worker. "
      + "Ошибка при сохранению данных hst_ERPRecipes")
  void receptIsFoundDoctorNotFoundDoctorIsCreateDataSavedToHstERPRecipesFailure() {

    Mockito.when(recipeRepository.findAllByLimit(Mockito.any()))
        .thenReturn(List.of(getRecipesViewMock()));
    Mockito.when(prescriptionRepository.existsByGuid(Mockito.any())).thenReturn(Boolean.FALSE);
    Mockito.when(medicalWorkerRepository.existsByLocalId(Mockito.anyString())).thenReturn(Boolean.TRUE);
    Mockito.when(transferRecipeAppConfig.getPrescriptionType()).thenReturn(getPrescriptionTypeMock());
    Mockito.when(transferRecipeAppConfig.getFundingSource()).thenReturn(getFundingSourceMock());
    Mockito.when(recipeResultRepository.saveAll(Mockito.any()))
        .thenThrow(new RuntimeException("Ошибка при сохранении в таблицу erp_prescription"));
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(MedicalWorker.class)))
        .thenReturn(getMedicalWorkerMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(Prescription.class)))
        .thenReturn(getPrescriptionMock());
    Mockito.when(converterPool.convertG(Mockito.any(Recipe.class), Mockito.eq(RecipeResult.class)))
        .thenReturn(getRecipeResultMock());

    transferRecipeService.run();

    Prescription expectedPrescription = getPrescriptionMock();
    ArgumentCaptor<Collection<Prescription>> prescriptionCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(prescriptionRepository, Mockito.times(1))
        .saveAll(prescriptionCaptor.capture());
    List<Prescription> actualPrescription = prescriptionCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedPrescription.getId(),
        actualPrescription.get(0).getId());
    Assertions.assertEquals(expectedPrescription.getGuid(),
        actualPrescription.get(0).getGuid());
    Assertions.assertEquals(expectedPrescription.getPrescribedMedicationGuid(),
        actualPrescription.get(0).getPrescribedMedicationGuid());
    Assertions.assertEquals(expectedPrescription.getSeries(),
        actualPrescription.get(0).getSeries());
    Assertions.assertEquals(expectedPrescription.getNumber(),
        actualPrescription.get(0).getNumber());
    Assertions.assertEquals(expectedPrescription.getDatePrescription(),
        actualPrescription.get(0).getDatePrescription());
    Assertions.assertEquals(expectedPrescription.getDateEnd(),
        actualPrescription.get(0).getDateEnd());
    Assertions.assertEquals(expectedPrescription.getFormTypeId(),
        actualPrescription.get(0).getFormTypeId());
    Assertions.assertEquals(expectedPrescription.getIsPaper(),
        actualPrescription.get(0).getIsPaper());
    Assertions.assertEquals(expectedPrescription.getPatientSnils(),
        actualPrescription.get(0).getPatientSnils());
    Assertions.assertEquals(expectedPrescription.getPatientSurname(),
        actualPrescription.get(0).getPatientSurname());
    Assertions.assertEquals(expectedPrescription.getPatientName(),
        actualPrescription.get(0).getPatientName());
    Assertions.assertEquals(expectedPrescription.getPatientPatronymic(),
        actualPrescription.get(0).getPatientPatronymic());
    Assertions.assertEquals(expectedPrescription.getPatientBirthday(),
        actualPrescription.get(0).getPatientBirthday());
    Assertions.assertEquals(expectedPrescription.getGenderId(),
        actualPrescription.get(0).getGenderId());
    Assertions.assertEquals(expectedPrescription.getPatientLocalId(),
        actualPrescription.get(0).getPatientLocalId());
    Assertions.assertEquals(expectedPrescription.getOrganizationId(),
        actualPrescription.get(0).getOrganizationId());
    Assertions.assertEquals(expectedPrescription.getDoctorId(),
        actualPrescription.get(0).getDoctorId());
    Assertions.assertEquals(expectedPrescription.getPrescriptionName(),
        actualPrescription.get(0).getPrescriptionName());
    Assertions.assertEquals(expectedPrescription.getPrescriptionTypeId(),
        actualPrescription.get(0).getPrescriptionTypeId());
    Assertions.assertEquals(expectedPrescription.getIsTrn(),
        actualPrescription.get(0).getIsTrn());
    Assertions.assertEquals(expectedPrescription.getSigna(),
        actualPrescription.get(0).getSigna());
    Assertions.assertEquals(expectedPrescription.getNumberDose(),
        actualPrescription.get(0).getNumberDose());
    Assertions.assertEquals(expectedPrescription.getSmnnId(),
        actualPrescription.get(0).getSmnnId());
    Assertions.assertEquals(expectedPrescription.getOkatoCode(),
        actualPrescription.get(0).getOkatoCode());
    Assertions.assertEquals(expectedPrescription.getPrescriptionStateId(),
        actualPrescription.get(0).getPrescriptionStateId());
    Assertions.assertEquals(expectedPrescription.getSchemeUid(),
        actualPrescription.get(0).getSchemeUid());
    Assertions.assertEquals(expectedPrescription.getMkbCode(),
        actualPrescription.get(0).getMkbCode());
    Assertions.assertEquals(expectedPrescription.getSoftwareName(),
        actualPrescription.get(0).getSoftwareName());
    Assertions.assertEquals(expectedPrescription.getFundingSourceId(),
        actualPrescription.get(0).getFundingSourceId());
    Assertions.assertEquals(expectedPrescription.getPercentageOfPayment(),
        actualPrescription.get(0).getPercentageOfPayment());
    Assertions.assertEquals(expectedPrescription.getPrivilegeId(),
        actualPrescription.get(0).getPrivilegeId());
    Assertions.assertEquals(expectedPrescription.getValidityId(),
        actualPrescription.get(0).getValidityId());
    Assertions.assertEquals(expectedPrescription.getSubdivisionId(),
        actualPrescription.get(0).getSubdivisionId());
    Assertions.assertEquals(expectedPrescription.getDosage(),
        actualPrescription.get(0).getDosage());
    Assertions.assertEquals(expectedPrescription.getDosageForm(),
        actualPrescription.get(0).getDosageForm());
    Assertions.assertEquals(expectedPrescription.getMnn(),
        actualPrescription.get(0).getMnn());
    Assertions.assertEquals(expectedPrescription.getIsSended(),
        actualPrescription.get(0).getIsSended());

    RecipeResult expectedRecipeResults = getRecipeResultMock();
    ArgumentCaptor<Collection<RecipeResult>> recipesResultsCaptor = ArgumentCaptor.forClass(Collection.class);
    Mockito.verify(recipeResultRepository, Mockito.times(1))
        .saveAll(recipesResultsCaptor.capture());
    List<RecipeResult> actualRecipeResults = recipesResultsCaptor.getValue().stream().toList();
    Assertions.assertEquals(expectedRecipeResults.getId(), actualRecipeResults.get(0).getId());
    Assertions.assertEquals(expectedRecipeResults.getRecipeGUID(),
        actualRecipeResults.get(0).getRecipeGUID());
    Assertions.assertEquals(expectedRecipeResults.getIsSend(), actualRecipeResults.get(0).getIsSend());
    Assertions.assertEquals(expectedRecipeResults.getSendDate().toLocalDate(),
        actualRecipeResults.get(0).getSendDate().toLocalDate());
  }

  @Test
  @DisplayName("Найден один рецепт. "
      + "Врач не найден. "
      + "Врач создан в таблице erp_medical_worker. "
      + "Ошибка при сохранению данных hst_ERPRecipes")
  void receptIsFoundDoctorNotFoundDoctorIsCreateDataSavedToErpPrescriptionFailure123() {

    Mockito.when(recipeRepository.findAll()).thenReturn(List.of());

    transferRecipeService.run();

    Mockito.verify(medicalWorkerRepository, Mockito.times(0))
        .existsByLocalId(Mockito.any());
    Mockito.verify(medicalWorkerRepository, Mockito.times(0))
        .saveAll(Mockito.any());
    Mockito.verify(medicalWorkerRepository, Mockito.times(0))
        .findBySnils(Mockito.any());
    Mockito.verify(transferRecipeAppConfig, Mockito.times(0))
        .getPrescriptionType();
    Mockito.verify(transferRecipeAppConfig, Mockito.times(0))
        .getFundingSource();
    Mockito.verify(prescriptionRepository, Mockito.times(0))
        .saveAll(Mockito.any());
    Mockito.verify(recipeResultRepository, Mockito.times(0))
        .saveAll(Mockito.any());

  }

  private Map<Integer, Integer> getFundingSourceMock() {
    return Map.of(1, 0, 2, 1);
  }

  private Map<String, Integer> getPrescriptionTypeMock() {
    return Map.of("Drug", 0, "nutrition", 1, "device", 2);
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

  private RecipeResult getRecipeResultMock() {

    return RecipeResult.builder()
        .sendDate(LocalDateTime.now())
        .recipeGUID(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        .isSend(Boolean.TRUE)
        .build();
  }
}
