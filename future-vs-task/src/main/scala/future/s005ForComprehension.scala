package future

object s005ForComprehension extends App {

  import Thread.sleep

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.concurrent.{Await, Future}

  def longRunningJob1: Int = { sleep(2000); println("running longRunningJob1"); 2 }
  def longRunningJob2: Int = { sleep(3000); println("running longRunningJob2"); 4 }
  def longRunningJob3: Int = { sleep(4000); println("running longRunningJob3"); 6 }

  def example1: Future[Int] =
    for {
      a <- Future(longRunningJob1)
      b <- Future(longRunningJob2)
      c <- Future(longRunningJob3)
    } yield a + b + c

  def example2: Future[Int] = {
    val futureA = Future(longRunningJob1)
    val futureB = Future(longRunningJob2)
    val futureC = Future(longRunningJob3)

    for {
      a <- futureA
      b <- futureB
      c <- futureC
    } yield a + b + c
  }

  Await.result(example2, 10.seconds)

}
