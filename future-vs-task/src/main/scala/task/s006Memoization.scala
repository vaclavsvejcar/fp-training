package task

import monix.eval.Task

object s006Memoization extends App{
  import monix.execution.Scheduler.Implicits.global

  val task = Task(println("hello"))

  task.runSyncUnsafe()
  task.runSyncUnsafe()

}
