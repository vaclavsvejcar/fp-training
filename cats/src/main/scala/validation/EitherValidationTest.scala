package validation

object EitherValidationTest extends App {

  val result: Either[Domain.FormError, Domain.Form] =
    EitherValidation.validateForm("user", "SuperTajneHeslo", "John", "Smith", 42)

  result match {
    case Left(error) => println(s"ERROR: ${error.msg}")
    case Right(form) => println(s"SUCCESS: $form")
  }
}
