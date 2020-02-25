package validation

trait EitherValidation {

  import Domain._

  def validateForm(
    username: String,
    password: String,
    firstName: String,
    lastName: String,
    age: Int
  ): Either[FormError, Form] = {

    for {
      validatedUserName  <- validateUserName(username)
      validatedPassword  <- validatePassword(password)
      validatedFirstName <- validateFirstName(firstName)
      validatedLastName  <- validateLastName(lastName)
      validatedAge       <- validateAge(age)
    } yield Form(validatedUserName, validatedPassword, validatedFirstName, validatedLastName, validatedAge)
  }

  def validateUserName(userName: String): Either[FormError, String] =
    Either.cond(
      userName.matches("^[a-zA-Z0-9]+$"),
      userName,
      FormError.UsernameHasSpecialChars
    )

  def validatePassword(password: String): Either[FormError, String] =
    Either.cond(
      password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"),
      password,
      FormError.PasswordDoesNotMeetCriteria
    )

  def validateFirstName(firstName: String): Either[FormError, String] =
    Either.cond(
      firstName.matches("^[a-zA-Z]+$"),
      firstName,
      FormError.FirstNameHasSpecialChars
    )

  def validateLastName(lastName: String): Either[FormError, String] =
    Either.cond(
      lastName.matches("^[a-zA-Z]+$"),
      lastName,
      FormError.LastNameHasSpecialChars
    )

  def validateAge(age: Int): Either[FormError, Int] =
    Either.cond(
      age >= 18 && age <= 75,
      age,
      FormError.AgeIsInvalid
    )
}

object EitherValidation extends EitherValidation
