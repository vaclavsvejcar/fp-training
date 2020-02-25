package validation

object Domain {

  case class Form(username: String, password: String, firstName: String, lastName: String, age: Int)

  sealed abstract class FormError(val msg: String)
  object FormError {
    case object UsernameHasSpecialChars extends FormError("Username cannot contain special characters")
    case object PasswordDoesNotMeetCriteria extends FormError("Password must be at least 10 characters long, including an uppercase and a lowercase letter, one number and one special character.")
    case object FirstNameHasSpecialChars extends FormError("First name cannot contain special characters")
    case object LastNameHasSpecialChars extends FormError("Last name cannot contain special characters")
    case object AgeIsInvalid extends FormError("You must be aged 18 and not older than 75 to use our services.")
  }

}
