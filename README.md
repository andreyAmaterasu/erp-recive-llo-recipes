# erp-recive-llo-recipes

Сервис для интеграции портала выписки и платформы e-Rp

### Используемое ПО
- Java 17
- Spring Boot 3.1.3
- MSSQL JDBC 12.4.1.jre11
- PostgreSQL JDBC Driver 42.6.0

## Сборка
```sh
mvn clean install 
```

### Описание сервиса
Сервис **erp-recive-llo-recipes** это сервис-scheduler, выполняющий процессы по расписанию. 
Сервис имеет два процесса: TransferRecipe и TransfeRelease (будет реализован позже).

### Описание параметров конфигурации (application.yaml)
Настройки подключения к БД: \
Подключение к БД портала выписки: \
<span style="color:green;">datasource.checkout-portal.url</span> - адрес подключения к БД \
<span style="color:green;">datasource.checkout-portal.host</span> - наименование хоста \
<span style="color:green;">datasource.checkout-portal.database</span> - наименование базы данных \
<span style="color:green;">datasource.checkout-portal.username</span> - имя пользователя БД \
<span style="color:green;">datasource.checkout-portal.password</span> - пароль пользователя БД

Подключение к БД e-Rp: \
<span style="color:green;">datasource.erp.url</span> - адрес подключения к БД \
<span style="color:green;">datasource.erp.host</span> - наименование хоста \
<span style="color:green;">datasource.erp.port</span> - номер порта \
<span style="color:green;">datasource.erp.database</span> - наименование базы данных \
<span style="color:green;">datasource.erp.username</span> - имя пользователя БД \
<span style="color:green;">datasource.erp.password</span> - пароль пользователя БД

Общие настройки для работы сервиса: \
<span style="color:green;">app.transfer-recipe.prescription-type</span> - сопоставление типа назначения (Портал выписки: e-Rp) \
<span style="color:green;">app.transfer-recipe.funding-source</span> - сопоставление кода источника финансирования (Портал выписки: e-Rp) 

### Алгоритм работы сервиса
**1 -** Обращение к представлению V_hst_ERPRecipes и выгрузка рецептов, которые еще не были отправлены в e-Rp \
**2 -** Проверка, существуют ли данные врача в базе e-Rp для передаваемого рецепта. Проверка происходит по условию:
erp_medical_worker.local_id == V_hst_ERPRecipes.localId \
**2.1 -** Если врача нет в базе e-Rp, то происходит добавление врача в базу по следующему сопоставлению:
<details>
<summary><i>Нажмите, чтобы развернуть</i></summary>
<table style="text-align:center">
<tr><th>erp_medical_worker</th><th>V_hst_ERPRecipes</th></tr>
<tr><td>local_Id</td><td>localId</td></tr>
<tr><td>surname</td><td>doctor_surname</td></tr>
<tr><td>name</td><td>doctor_name</td></tr>
<tr><td>patronymic</td><td>doctor_patronymic</td></tr>
<tr><td>birth_date</td><td>birthDate</td></tr>
<tr><td>snils</td><td>doctor_snils</td></tr>
<tr><td>position_code</td><td>positionСode</td></tr>
<tr><td>speciality_code</td><td>specialityCode</td></tr>
<tr><td>department_code</td><td>departmentCode</td></tr>
<tr><td>medical_worker_type_id</td><td>1</td></tr>
<tr><td>organization_id</td><td>select o.id from organizations o where o.federal_oid  = :organization_oid, где<br>:organization_oid - organization_oid</td></tr>
</table>
</details>

**2.2 -** Если врач есть, то переходим к следующему пункту \
**3 -** Происходит регистрация рецепта в базе e-Rp, путем сохранения в таблице erp_prescription рецепта, сформированного по следующему сопоставлению:
<details>
<summary><i>Нажмите, чтобы развернуть</i></summary>
<table style="text-align:center">
<tr><th>erp_prescription</th><th>V_hst_ERPRecipes</th></tr>
<tr><td>guid</td><td>uid</td></tr>
<tr><td>prescribed_medication_guid</td><td>prescribedMedicationGuid</td></tr>
<tr><td>series</td><td>series</td></tr>
<tr><td>number</td><td>number</td></tr>
<tr><td>date_prescription</td><td>dateCreate</td></tr>
<tr><td>date_end</td><td>dateEnd</td></tr>
<tr><td>form_type_id</td><td>3</td></tr>
<tr><td>is_paper</td><td>isPaper</td></tr>
<tr><td>patient_snils</td><td>snils</td></tr>
<tr><td>patient_surname</td><td>surname</td></tr>
<tr><td>patient_name</td><td>name</td></tr>
<tr><td>patient_patronomic</td><td>patronymic</td></tr>
<tr><td>patient_birthday</td><td>birthday</td></tr>
<tr><td>gender_id</td><td>gender</td></tr>
<tr><td>patient_local_id</td><td>localUid</td></tr>
<tr><td>organization_id</td><td>select o.id from organizations o where o.federal_oid  = :organization_oid, где<br>:organization_oid - organization_oid</td></tr>
<tr><td>doctor_id</td><td>select emw.id from erp_medical_worker emw where emw.snils = :doctor_snils, где<br>:doctor_snils - doctor_snils</td></tr>
<tr><td>prescription_name</td><td>prescriptionName</td></tr>
<tr><td>prescription_type_id</td><td>0, если :type = 'Drug'<br>1, если :type = 'nutrition'<br>2, если :type = 'device',<br>где :type - type</td></tr>
<tr><td>is_trn</td><td>isTrn</td></tr>
<tr><td>signa</td><td>signa</td></tr>
<tr><td>number_dose</td><td>numberDose</td></tr>
<tr><td>smnn_id</td><td>select es.id from erp_smnn es where es.code = :code, где<br>:code - code</td></tr>
<tr><td>okato_code</td><td>okatoCode</td></tr>
<tr><td>prescription_state_id</td><td>0</td></tr>
<tr><td>scheme_uid</td><td>schemeUid</td></tr>
<tr><td>mkb_code</td><td>mkbCode</td></tr>
<tr><td>software_name</td><td>softwareName</td></tr>
<tr><td>funding_source_id</td><td>0, если :fundingSourceCode = 1<br>1, если :fundingSourceCode = 2, где<br>:fundingSourceCode - fundingSourceCode</td></tr>
<tr><td>percentage_of_payment</td><td>percentageOfPayment</td></tr>
<tr><td>privilege_id</td><td>select ep.id from erp_privilege ep where ep.code = :privilegeCode, где<br>:privilegeCode - privilegeCode</td></tr>
<tr><td>validity_id</td><td>select ev.id from erp_validity ev where ev.code = :validityCode, где<br>:validityCode - validityCode</td></tr>
<tr><td>subdivision_id</td><td>select s.id from subdivision s where s.federal_oid = :subdivision_oid, где<br>:subdivision_oid - subdivision_oid</td></tr>
<tr><td>dosage</td><td>dosage</td></tr>
<tr><td>dosage_form</td><td>dosageForm</td></tr>
<tr><td>mnn</td><td>mnn</td></tr>
</table>
</details>

**4 -** После отправки рецептов в базу e-Rp, происходит добавление информации об отправленных рецептах в таблицу hst_ERPRecipes по следующему сопоставлению:
<details>
<summary><i>Нажмите, чтобы развернуть</i></summary>
<table style="text-align:center">
<tr><th>hst_ERPRecipes</th><th>Значение</th></tr>
<tr><td>RecipeGUID</td><td>V_hst_ERPRecipes.uid</td></tr>
<tr><td>isSendErp</td><td>1</td></tr>
<tr><td>Date_send</td><td>getdate()</td></tr>
</table>
</details>