package ru.kostromin.erprecivellorecipes.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mapstruct.factory.Mappers;
import ru.kostromin.erprecivellorecipes.converter.mapper.RecipeResultMapper;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.RecipeResult;

@TestInstance(Lifecycle.PER_METHOD)
class RecipeResultConverterTest {

  private final RecipeResultMapper mapper = Mappers.getMapper(RecipeResultMapper.class);

  private RecipeResultConverter converter;

  @BeforeEach
  void setup() {
    converter = new RecipeResultConverter(mapper);
  }

  @Test
  @DisplayName("Все поля в представлении журнала переданных рецептов заполнены")
  void allFieldsOfRecipeResultIsFilled() {
    RecipeResult recipeResultActual = converter.convertG(getRecipesViewMock(), RecipeResult.class);
    final RecipeResult recipeResultExpected = getRecipeResultMock();
    Assertions.assertEquals(recipeResultActual.getRecipeGUID(), recipeResultExpected.getRecipeGUID());
    Assertions.assertEquals(recipeResultActual.getIsSend(), recipeResultExpected.getIsSend());
    Assertions.assertEquals(recipeResultActual.getSendDate().toLocalDate(),
        recipeResultExpected.getSendDate().toLocalDate());
  }

  private RecipeResult getRecipeResultMock() {

    return RecipeResult.builder()
        .recipeGUID(UUID.fromString("00000000-0000-0000-0000-000000000002"))
        .isSend(Boolean.TRUE)
        .sendDate(LocalDateTime.now())
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
}
