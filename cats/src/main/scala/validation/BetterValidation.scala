package validation

import cats.data.ValidatedNec

trait BetterValidation {

  import Domain._
  import cats.implicits._

  type ValidationResult[A] = ValidatedNec[FormError, A]

  def validateForm(
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    age: Int
  ): ValidationResult[Form] = {
    (
      validateUserName(username),
      validatePassword(password),
      validateFirstName(firstName),
      validateLastName(lastName),
      validateAge(age)
    ).mapN(Form)
  }

  private def validateUserName(userName: String): ValidationResult[String] =
    if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNec
    else FormError.UsernameHasSpecialChars.invalidNec

  private def validatePassword(password: String): ValidationResult[String] =
    if (password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) password.validNec
    else FormError.PasswordDoesNotMeetCriteria.invalidNec

  private def validateFirstName(firstName: String): ValidationResult[String] =
    if (firstName.matches("^[a-zA-Z]+$")) firstName.validNec
    else FormError.FirstNameHasSpecialChars.invalidNec

  private def validateLastName(lastName: String): ValidationResult[String] =
    if (lastName.matches("^[a-zA-Z]+$")) lastName.validNec
    else FormError.LastNameHasSpecialChars.invalidNec

  private def validateAge(age: Int): ValidationResult[Int] =
    if (age >= 18 && age <= 75) age.validNec
    else FormError.AgeIsInvalid.invalidNec
}

object BetterValidation extends BetterValidation
