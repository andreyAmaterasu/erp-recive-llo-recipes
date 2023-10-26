package ru.kostromin.erprecivellorecipes.converter.mapper;

import io.vavr.control.Try;
import lombok.Setter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kostromin.erprecivellorecipes.config.TransferRecipeAppConfig;
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

/**
 * Маппер {@link Recipe} в {@link Prescription}
 */
@Setter(onMethod = @__({@Autowired}))
@Mapper(componentModel = ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public abstract class RecipeMapper {

  protected OrganizationRepository organizationRepository;

  protected MedicalWorkerRepository medicalWorkerRepository;

  protected SmnnRepository smnnRepository;

  protected PrivilegeRepository privilegeRepository;

  protected ValidityRepository validityRepository;

  protected SubdivisionRepository subdivisionRepository;

  protected TransferRecipeAppConfig transferRecipeAppConfig;

  @Mapping(target = "guid", source = "uid")
  @Mapping(target = "datePrescription", source = "dateCreate")
  @Mapping(target = "formTypeId", constant = "3")
  @Mapping(target = "patientSnils", source = "snils")
  @Mapping(target = "patientSurname", source = "surname")
  @Mapping(target = "patientName", source = "name")
  @Mapping(target = "patientPatronymic", source = "patronymic")
  @Mapping(target = "patientBirthday", source = "birthDate")
  @Mapping(target = "genderId", source = "gender")
  @Mapping(target = "prescriptionStateId", constant = "0")
  @Mapping(target = "percentageOfPayment", source = "percentageOfPayment", qualifiedByName = "mapStringToInteger")
  public abstract Prescription prescriptionFromRecipesView(Recipe recipe);

  @AfterMapping
  protected void updateFields(Recipe recipe, @MappingTarget Prescription target) {

    target.setPatientLocalId(String.valueOf(recipe.getLocalUid()));
    target.setPercentageOfPayment(mapStringToInteger(recipe.getPercentageOfPayment()));
    target.setIsSended(Boolean.TRUE);
    target.setOrganizationId(getOrganizationId(recipe.getOrganizationOid()));
    target.setDoctorId(getMedicalWorkerId(recipe.getDoctorSnils()));
    target.setSmnnId(getSmnnId(recipe.getCode()));
    target.setPrivilegeId(getPrivilegeId(recipe.getPrivilegeCode()));
    target.setValidityId(getValidityId(recipe.getValidityCode()));
    target.setSubdivisionId(getSubdivisionId(recipe.getSubdivisionOid()));
    target.setPrescriptionTypeId(transferRecipeAppConfig.getPrescriptionType()
        .get(recipe.getType()));
    target.setFundingSourceId(transferRecipeAppConfig.getFundingSource()
        .get(recipe.getFundingSourceCode()));
  }

  @Named("mapStringToInteger")
  protected Integer mapStringToInteger(String stringValue) {

    return Try.of(() -> Integer.valueOf(stringValue))
        .getOrElse(0);
  }

  protected Integer getOrganizationId(String organizationOid) {

    return organizationRepository.findByFederalOid(organizationOid)
        .map(Organization::getId)
        .orElse(0);
  }

  protected Integer getMedicalWorkerId(String snils) {

    return medicalWorkerRepository.findBySnils(snils)
        .map(MedicalWorker::getId)
        .orElse(0);
  }

  protected Integer getSmnnId(String code) {

    return smnnRepository.findByCode(code)
        .map(Smnn::getId)
        .orElse(0);
  }

  protected Integer getPrivilegeId(String privilegeCode) {

    return privilegeRepository.findByCode(privilegeCode)
        .map(Privilege::getId)
        .orElse(0);
  }

  protected Integer getValidityId(String validityCode) {

    return validityRepository.findByCode(validityCode)
        .map(Validity::getId)
        .orElse(0);
  }

  protected Integer getSubdivisionId(String subdivisionOid) {

    return subdivisionRepository.findByFederalOid(subdivisionOid)
        .map(Subdivision::getId)
        .orElse(0);
  }
}
