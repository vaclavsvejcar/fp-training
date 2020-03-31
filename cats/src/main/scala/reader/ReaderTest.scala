package reader

import cats.data.Reader

object ReaderTest extends App {

  trait Logger { def log(msg: String): Unit }
  class DummyLogger extends Logger {
    override def log(msg: String): Unit = println(s"LOG: $msg")
  }

  case class User(id: String)
  case class Address(city: String)

  def findUser(id: String): Reader[Logger, User] = Reader { logger =>
    logger.log(s"Searching for user with ID '$id'")
    User(id)
  }

  def findAddress(user: User): Reader[Logger, Address] = Reader { logger =>
    logger.log(s"Searching address for user '$user'")
    Address("Prague")
  }

  val address: Reader[Logger, Address] = for {
    user    <- findUser("123")
    address <- findAddress(user)
  } yield address

  val result: Address = address.run(new DummyLogger())
  println(s"RESULT: $result")

}
