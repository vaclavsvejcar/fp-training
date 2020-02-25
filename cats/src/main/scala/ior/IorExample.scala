package ior

object IorExample extends App {

  import cats.data._
  import cats.implicits._

  type Failures = NonEmptyChain[String]
  case class Username(value: String) extends AnyVal
  case class Password(value: String) extends AnyVal
  case class User(name: Username, pw: Password)

  def validateUser(name: String, password: String): Ior[Failures, User] =
    (validateUsername(name), validatePassword(password)).mapN(User)

  def validateUsername(u: String): Ior[Failures, Username] = {
    if (u.isEmpty)
      NonEmptyChain.one("Can't be empty").leftIor
    else if (u.contains("."))
      Ior.both(NonEmptyChain.one("Dot in name is deprecated"), Username(u))
    else
      Username(u).rightIor
  }

  def validatePassword(p: String): Ior[Failures, Password] = {
    if (p.length < 8)
      NonEmptyChain.one("Password too short").leftIor
    else if (p.length < 10)
      Ior.both(NonEmptyChain.one("Password should be longer"), Password(p))
    else
      Password(p).rightIor
  }

  val result: Ior[Failures, User] = validateUser("john.smith", "password12")
  println(result)

}
