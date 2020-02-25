

object s001_option extends App {

  val noValue: Option[Nothing] = None
  val someValue: Option[Int] = Some(42)

  //val value: Option[Int] = Some(42)
  /*val value: Option[Int] = None
  value match {
    case Some(answer) => println(s"The answer is $answer")
    case None         => println("No answer")
  }*/

  val map = new java.util.HashMap[String, String]()
  val result = Option(map.get("foo"))
  println(result)
}
