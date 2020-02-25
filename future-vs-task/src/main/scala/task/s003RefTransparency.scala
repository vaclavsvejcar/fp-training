package task

object s003RefTransparency extends App {

  import monix.eval.Task
  import monix.execution.Scheduler.Implicits.global

  def version1: Task[Unit] = {
    for {
      _ <- Task(println("hello"))
      _ <- Task(println("hello"))
      _ <- Task(println("hello"))
    } yield ()
  }

  def version2: Task[Unit] = {
    val task = Task(println("hello"))
    for {
      _ <- task
      _ <- task
      _ <- task
    } yield ()
  }

  version2.runSyncUnsafe()

}
