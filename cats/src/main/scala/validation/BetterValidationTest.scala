package validation

import cats.data.Validated

object BetterValidationTest extends App {
  val result: BetterValidation.ValidationResult[Domain.Form] =
    BetterValidation.validateForm("user#$", "SuperTajneHeslo", "John", "Smith", 42)

  result match {
    case Validated.Invalid(errors) =>
      println(s"ERRORS: ${errors.map(_.msg).map("\n - " + _).toNonEmptyList.toList.mkString}")
    case Validated.Valid(form) => println(s"SUCCESS: $form")
  }
}
