package task

import monix.execution.Cancelable

import scala.concurrent.Future

object s002TaskExample extends App {

  import monix.eval.Task
  import monix.execution.Scheduler.Implicits.global

  val task1 = Task { println("hello"); 42 }


  println(task1.runSyncUnsafe()) // run synchronously


  // run asynchronously
  task1.runAsync {
    case Right(result) => println(result)
    case Left(error)   => ???
  }


  // run to Scala Future
  val taskFt: Future[Int] = task1.runToFuture

}
