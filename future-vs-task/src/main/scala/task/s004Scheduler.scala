package task

import monix.eval.Task

object s004Scheduler extends App {

  def transformTask(strFt: Task[String]): Task[String] =
    for {
      str <- strFt
    } yield str.toUpperCase

  def composeTasks: Task[String] =
    for {
      f1 <- Task("hello")
      f2 <- Task("world")
    } yield f1 + f2
}
