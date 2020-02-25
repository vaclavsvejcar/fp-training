import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

def blockingOperation: String = { Thread.sleep(5000); "result" }

val blockingOperationFt: Future[String] = Future(blockingOperation)

println("Executing blocking operation")
blockingOperationFt.onComplete {
  case Success(result) => println("RESULT: " + result)
  case Failure(ex)     => println("Something went wrong:" + ex)
}
println("Some code not blocked by blocking call")




println("Executing blocking operation")
val result: String = Await.result(blockingOperationFt, 5.seconds)




val result2: Future[String] = for {
  res <- blockingOperationFt
} yield res.toUpperCase
