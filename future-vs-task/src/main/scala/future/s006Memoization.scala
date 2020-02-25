package future

object s006Memoization extends App {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.concurrent.{Await, Future}

  val f = Future(println("hello"))
  Await.result(f, Duration.Inf)
  Await.result(f, Duration.Inf)

}
