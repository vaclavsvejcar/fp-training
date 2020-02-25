import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.data.OptionT
import cats.instances.future._

class s004_transformers {

  case class User(id: Long)
  case class Address(city: String)

  def findUserById(id: Long): Future[Option[User]] = ???
  def findAddressByUser(user: User): Future[Option[Address]] = ???

  def findAddressByUserId(id: Long): Future[Option[Address]] =
    for {
      user    <- findUserById(id)
      address <- findAddressByUser(user)
    } yield address

}
