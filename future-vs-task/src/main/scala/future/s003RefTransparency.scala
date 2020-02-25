package future

object s003RefTransparency extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  def version1() = {
    for {
      _ <- Future(println("hello"))
      _ <- Future(println("hello"))
      _ <- Future(println("hello"))
    } yield ()
  }

  def version2() = {
    val future = Future(println("hello"))
    for {
      _ <- future
      _ <- future
      _ <- future
    } yield ()
  }

  //version1()
  version2()

}
