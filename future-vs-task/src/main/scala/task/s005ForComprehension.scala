package task

import monix.eval.Task

object s005ForComprehension extends App {
  import monix.execution.Scheduler.Implicits.global

  def longRunningJob1: Int = { Thread.sleep(2000); println("executing job 1"); 2 }
  def longRunningJob2: Int = { Thread.sleep(2000); println("executing job 2"); 4 }
  def longRunningJob3: Int = { Thread.sleep(2000); println("executing job 3"); 6 }

  def version1: Task[Int] = {
    for {
      a <- Task(longRunningJob1)
      b <- Task(longRunningJob2)
      c <- Task(longRunningJob3)
    } yield a + b + c
  }

  def version2: Task[Int] = {
    val task1 = Task(longRunningJob1)
    val task2 = Task(longRunningJob2)
    val task3 = Task(longRunningJob3)

    for {
      a <- task1
      b <- task2
      c <- task3
    } yield a + b + c
  }

  val version3 = Task.gather(Seq(Task(longRunningJob1), Task(longRunningJob2), Task(longRunningJob3))).map(_.sum)

  version3.runSyncUnsafe()

}
