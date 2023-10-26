package ru.kostromin.erprecivellorecipes.data.checkoutportal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

/**
 * Представление для журнала переданных рецептов
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"recipeGUID"})
@Entity(name = "hst_ERPRecipes")
public class RecipeResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Type(type="org.hibernate.type.UUIDCharType")
  @Column(name = "RecipeGUID")
  private UUID recipeGUID;

  @Column(name = "isSendErp")
  private Boolean isSend;

  @Column(name = "Date_send")
  private LocalDateTime sendDate;
}
