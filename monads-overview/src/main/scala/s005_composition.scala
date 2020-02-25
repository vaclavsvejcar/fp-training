import cats.MonadError

import scala.util.Try

class s005_composition {

  case class JsonError()
  case class Json()
  case class ParseException(message: String) extends Exception

  def readFile(path: String): Try[String] = ???
  def toJson(str: String): Either[JsonError, Json] = ???

  for {
    fileContent <- readFile("foo.json")
    parsedFile  <- toJson(fileContent)
  } yield parsedFile
}
