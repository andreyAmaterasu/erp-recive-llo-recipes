SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('V_hst_ERPRecipes') IS NOT NULL DROP VIEW [dbo].[V_hst_ERPRecipes]
GO

CREATE VIEW [dbo].[V_hst_ERPRecipes] AS
select
    Recipe.RecipeGUID as [uid], -- uid
NewID() as prescribedMedicationGuid, -- prescribedMedicationGuid
Recipe.Series_Recipe as series, -- series
Recipe.Num_Recipe as number, -- number
convert(varchar, Recipe.Date_VR, 23) as dateCreate, -- dateCreate
convert(varchar, (dateadd(dd, 90, Recipe.Date_VR)), 23) as dateEnd,-- dateEnd
'Frm1481U04' as formType, -- formType
'false' as isPaper, -- isPaper
-- patient
Recipe.PersonGUID as localUid, -- localUid
Recipe.PFAMILY as surname, -- surname
Recipe.PName as name, -- name
Recipe.PPatronymic as patronymic, -- patronymic
convert(varchar, Recipe.birthday, 23) as birthday, -- birthday
Recipe.gender as gender, -- gender
Replace((Replace(Recipe.snils, '-', '')), ' ', '') as snils, -- snils,
Recipe.FCOD as privilegeCode, -- privilegeCode
-- organization
Recipe.M_NameS as shortName, -- shortName
Recipe.M_NameF as fullName, -- fullName
Recipe.C_OGRN as ogrn, -- ogrn
Recipe.Lic as organization_oid, -- oid
-- subdivision
Recipe.depart_name as subdivision_name, -- name
Recipe.depart_oid as subdivision_oid, -- oid
Recipe.depart_kind_id as kindCode, -- kindCode
-- doctor
Recipe.UGUID as localId, -- localId
convert(varchar, Recipe.DR_V, 23) as birthDate, -- birthDate
Recipe.Code as positionCode, -- positionCode
'27' as specialityCode,  -- specialityCode
Recipe.depart_oid as departmentCode, -- departmentCode
Recipe.Fam_V as doctor_surname, -- surname
Recipe.Im_V as doctor_name, -- name
Recipe.OT_V as doctor_patronymic, -- patronymic
Replace((Replace(Recipe.SS, '-', '')), ' ', '') as doctor_snils, --  snils
-- medication
Recipe.Name_Med as prescriptionName, -- prescriptionName
Recipe.signa as signa, -- signa
KV_ALL as numberDose, -- numberDose
Recipe.D_LS as dosage, --dosage
Recipe.C_LF as dosageForm, --dosageForm
Recipe.typeLS as type, -- type
Recipe.CodeCmnn as code, -- code
'false' as isTrn, -- isTrn
Recipe.C_OKATO as okatoCode, -- okatoCode
NewID() as schemeUid, -- schemeUid
Recipe.DS as mkbCode, -- mkbCode
Recipe.C_PR_LR as percentageOfPayment,  -- percentageOfPayment
Recipe.C_FINL as fundingSourceCode, -- fundingSourceCode
'Portal LLO' as softwareName, -- softwareName
Recipe.validityCode as validityCode, -- validityCode,
'Registered' as state -- state
from (
    select
    RecipeGUID,
    Series_Recipe,
    Num_Recipe,
    Date_VR,
    PersonGUID,
    oms_Person.FAMILY as PFAMILY,
    oms_Person.Name as PName,
    oms_Person.Patronymic as PPatronymic,
    oms_Person.DR as birthday,
    oms_Person.W as gender,
    oms_Person.SS as snils,
    type_catl.FCOD,
    oms_LPU.M_NameS,
    oms_LPU.M_NameF,
    oms_LPU.C_OGRN,
    oms_LPU.C_INN,
    oms_LPU.Fam_gv + ' ' + oms_LPU.IM_GV + ' ' + oms_LPU.OT_GV as directorFullName,
    '' as directorPhone,
    oms_LPU.Lic,
    oms_LPU.adres,
    hst_FRMO_StructuralUnits.depart_name,
    hst_FRMO_StructuralUnits.depart_oid,
    hst_FRMO_StructuralUnits.depart_kind_id,
    hlt_LPUDoctor.UGUID,
    hlt_LPUDoctor.DR as DR_V,
    nsi.hst_PRVD.Code,
    hlt_LPUDoctor.Fam_V,
    hlt_LPUDoctor.IM_V,
    hlt_LPUDoctor.OT_V,
    hlt_LPUDoctor.SS,
    oms_LS.Name_Med,
    hlt_PolyclinicRecipeFederalLG.signa,
    hlt_PolyclinicRecipeFederalLG.KV_ALL,
    oms_LS.CodeCmnn,
    oms_LS.D_LS,
    oms_LF.C_LF,
    case
    when oms_LsType.LsTypeName = '0010 Лекарственные средства' THEN 'Drug'
    when oms_LsType.LsTypeName = '0010 Изделия медицинского назначения' THEN 'device'
    when oms_LsType.LsTypeName = '0005 Продукты лечебного и профилактического назначения' THEN 'nutrition'
    end as typeLS,
    oms_OKATO.C_OKATO,
    oms_MKB.DS,
    oms_Finl.C_FINL,
    oms_PR_LR.C_PR_LR,
    oms_Period.Code as validityCode
    from hlt_PolyclinicRecipeFederalLG
    join oms_Person on hlt_PolyclinicRecipeFederalLG.SS = oms_Person.SS
    join oms_KATL on hlt_PolyclinicRecipeFederalLG.rf_KATLID = oms_KATL.KATLID
    join type_catl on oms_KATL.C_KATL = type_catl.C_KATL
    join hlt_LPUDoctor on hlt_LPUDoctor.LPUDoctorID = hlt_PolyclinicRecipeFederalLG.rf_LPUDoctorID
    join hst_LPUDoctor on hst_LPUDoctor.rf_LPUDoctorID = hlt_LPUDoctor.LPUDoctorID
    join oms_LPU on hlt_LPUDoctor.rf_LPUID = oms_LPU.LPUID
    join hst_FRMO_StructuralUnits on hst_FRMO_StructuralUnits.FRMO_StructuralUnitsID = hst_LPUDoctor.rf_FRMO_StructuralUnitsID
    join oms_PRVS on hlt_LPUDoctor.rf_PRVSID = oms_PRVS.PRVSID
    join nsi.hst_PRVD on nsi.hst_PRVD.PRVDID = hst_LPUDoctor.rf_PRVDID
    join oms_LS on oms_LS.Nomk_ls = hlt_PolyclinicRecipeFederalLG.NOMK_LS
    join oms_LF on hlt_PolyclinicRecipeFederalLG.rf_LFID = oms_LF.LFID
    join oms_LsType on oms_LS.rf_LSTypeID = oms_LsType.LsTypeID
    join oms_OKATO on oms_OKATO.OKATOID = oms_LPU.rf_OKATOID
    join oms_MKB on hlt_PolyclinicRecipeFederalLG.rf_MKBID = oms_MKB.MKBID
    join oms_Finl on hlt_PolyclinicRecipeFederalLG.rf_FinlID = oms_Finl.FinlID
    join oms_PR_LR on hlt_PolyclinicRecipeFederalLG.rf_PR_LRID = oms_PR_LR.PR_LRID
    join oms_Period on oms_Period.PeriodID = hlt_PolyclinicRecipeFederalLG.rf_PeriodID
    join oms_StatusLPURecipe on hlt_PolyclinicRecipeFederalLG.rf_StatusLPURecipeID = oms_StatusLPURecipe.StatusLPURecipeID
    where oms_StatusLPURecipe.Status_Name in ('Получен из региональной МИС', 'выписан') and Date_VR > '2023-01-01'
    union
    select
    RecipeGUID,
    Series_Recipe,
    Num_Recipe,
    Date_VR,
    PersonGUID,
    oms_PersonRegLG.FAMILY as PFAMILY,
    oms_PersonRegLG.Name as PName,
    oms_PersonRegLG.Patronymic as PPatronymic,
    oms_PersonRegLG.DR as birthday,
    oms_PersonRegLG.W as gender,
    oms_PersonRegLG.SS as snils,
    type_catl.FCOD,
    oms_LPU.M_NameS,
    oms_LPU.M_NameF,
    oms_LPU.C_OGRN,
    oms_LPU.C_INN,
    oms_LPU.Fam_gv + ' ' + oms_LPU.IM_GV + ' ' + oms_LPU.OT_GV as directorFullName,
    '' as directorPhone,
    oms_LPU.Lic,
    oms_LPU.adres,
    hst_FRMO_StructuralUnits.depart_name,
    hst_FRMO_StructuralUnits.depart_oid,
    hst_FRMO_StructuralUnits.depart_kind_id,
    hlt_LPUDoctor.UGUID,
    hlt_LPUDoctor.DR as DR_V,
    nsi.hst_PRVD.Code,
    hlt_LPUDoctor.Fam_V,
    hlt_LPUDoctor.Im_V,
    hlt_LPUDoctor.OT_V,
    hlt_LPUDoctor.SS,
    oms_LS.Name_Med,
    hlt_PolyclinicRecipeRegionLG.signa,
    hlt_PolyclinicRecipeRegionLG.KV_ALL,
    oms_LS.CodeCmnn,
    oms_LS.D_LS,
    oms_LF.C_LF,
    case
    when oms_LsType.LsTypeName = '0010 Лекарственные средства' THEN 'Drug'
    when oms_LsType.LsTypeName = '0010 Изделия медицинского назначения' THEN 'device'
    when oms_LsType.LsTypeName = '0005 Продукты лечебного и профилактического назначения' THEN 'nutrition'
    end as typeLS,
    oms_OKATO.C_OKATO,
    oms_MKB.DS,
    oms_Finl.C_FINL,
    oms_PR_LR.C_PR_LR,
    oms_Period.Code as validityCode
    from hlt_PolyclinicRecipeRegionLG
    join oms_PersonRegLG on hlt_PolyclinicRecipeRegionLG.SS = oms_PersonRegLG.SS
    join oms_KATL on hlt_PolyclinicRecipeRegionLG.rf_KATLID = oms_KATL.KATLID
    join type_catl on oms_KATL.C_KATL = type_catl.C_KATL
    join hlt_LPUDoctor on hlt_LPUDoctor.LPUDoctorID = hlt_PolyclinicRecipeRegionLG.rf_LPUDoctorID
    join hst_LPUDoctor on hst_LPUDoctor.rf_LPUDoctorID = hlt_LPUDoctor.LPUDoctorID
    join oms_LPU on hlt_LPUDoctor.rf_LPUID = oms_LPU.LPUID
    join hst_FRMO_StructuralUnits on hst_FRMO_StructuralUnits.FRMO_StructuralUnitsID = hst_LPUDoctor.rf_FRMO_StructuralUnitsID
    join oms_PRVS on hlt_LPUDoctor.rf_PRVSID = oms_PRVS.PRVSID
    join nsi.hst_PRVD on nsi.hst_PRVD.PRVDID = hst_LPUDoctor.rf_PRVDID
    join oms_LS on oms_LS.Nomk_ls = hlt_PolyclinicRecipeRegionLG.NOMK_LS
    join oms_LF on hlt_PolyclinicRecipeRegionLG.rf_LFID = oms_LF.LFID
    join oms_LsType on oms_LS.rf_LSTypeID = oms_LsType.LsTypeID
    join oms_OKATO on oms_OKATO.OKATOID = oms_LPU.rf_OKATOID
    join oms_MKB on hlt_PolyclinicRecipeRegionLG.rf_MKBID = oms_MKB.MKBID
    join oms_Finl on hlt_PolyclinicRecipeRegionLG.rf_FinlID = oms_Finl.FinlID
    join oms_PR_LR on hlt_PolyclinicRecipeRegionLG.rf_PR_LRID = oms_PR_LR.PR_LRID
    join oms_Period on oms_Period.PeriodID = hlt_PolyclinicRecipeRegionLG.rf_PeriodID
    join oms_StatusLPURecipe on hlt_PolyclinicRecipeRegionLG.rf_StatusLPURecipeID = oms_StatusLPURecipe.StatusLPURecipeID
    where oms_StatusLPURecipe.Status_Name in ('Получен из региональной МИС', 'выписан') and Date_VR > '2023-01-01'
    ) as Recipe
    left join hst_ERPRecipes on hst_ERPRecipes.RecipeGUID = Recipe.RecipeGUID
where hst_ERPRecipes.isSendErp is null or hst_ERPRecipes.isSendErp = 0
GO