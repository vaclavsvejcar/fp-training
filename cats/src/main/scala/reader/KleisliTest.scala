package reader

import cats.data.Kleisli
import cats.implicits._

object KleisliTest extends App {

  trait Logger { def log(msg: String): Unit }
  class DummyLogger extends Logger {
    override def log(msg: String): Unit = println(s"LOG: $msg")
  }

  case class User(id: String)
  case class Address(city: String)

  def findUser(id: String): Kleisli[Option, Logger, User] = Kleisli { logger =>
    logger.log(s"Searching for user with ID '$id'")
    Some(User(id))
  }

  def findAddress(user: User): Kleisli[Option, Logger, Address] = Kleisli { logger =>
    logger.log(s"Searching address for user '$user'")
    Some(Address("Prague"))
  }

  val address: Kleisli[Option, Logger, Address] = for {
    user    <- findUser("123")
    address <- findAddress(user)
  } yield address

  val result: Option[Address] = address.run(new DummyLogger())
  println(s"RESULT: $result")

}
