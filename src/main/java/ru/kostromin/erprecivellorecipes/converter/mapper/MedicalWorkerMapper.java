package ru.kostromin.erprecivellorecipes.converter.mapper;

import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kostromin.erprecivellorecipes.data.checkoutportal.entity.Recipe;
import ru.kostromin.erprecivellorecipes.data.erp.entity.MedicalWorker;
import ru.kostromin.erprecivellorecipes.data.erp.entity.Organization;
import ru.kostromin.erprecivellorecipes.data.erp.repository.OrganizationRepository;

/**
 * Маппер {@link Recipe} в {@link MedicalWorker}
 */
@Setter(onMethod = @__({@Autowired}))
@Mapper(componentModel = ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public abstract class MedicalWorkerMapper {

  protected OrganizationRepository organizationRepository;

  @Mapping(target = "surname", source = "doctorSurname")
  @Mapping(target = "name", source = "doctorName")
  @Mapping(target = "patronymic", source = "doctorPatronymic")
  @Mapping(target = "birthDate", source = "doctorBirthDate")
  @Mapping(target = "snils", source = "doctorSnils")
  @Mapping(target = "medicalWorkerTypeId", constant = "1")
  public abstract MedicalWorker medicalWorkerFromRecipesView(Recipe recipe);

  @AfterMapping
  protected void updateFields(Recipe recipe, @MappingTarget MedicalWorker target) {
    target.setOrganizationId(getOrganizationId(recipe.getOrganizationOid()));
  }

  protected Integer getOrganizationId(String organizationOid) {

    return organizationRepository.findByFederalOid(organizationOid)
        .map(Organization::getId)
        .orElse(0);
  }
}
