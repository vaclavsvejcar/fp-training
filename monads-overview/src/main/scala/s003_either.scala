

object s003_either extends App {

  def parseInt(raw: String): Either[String, Int] =
    try Right(raw.toInt)
    catch {
      case ex: NumberFormatException => Left(s"Cannot parse input: $raw, reason: $ex")
    }

  val result = parseInt("foo")
  println(result)

}
