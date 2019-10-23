import cats.free.Free
import cats.{Id, ~>}
import free.algebra._

object s001LiftF {

  type SingleA[T] = Free[InOutA, T]

  def printLine(text: String): Free[InOutA, Unit] = Free.liftF(InOutA.PrintLine(text))

  object InOut2Id extends (InOutA ~> Id) {
    override def apply[A](fa: InOutA[A]): A = fa match {
      case InOutA.PrintLine(text) => println(text)
    }
  }

  val result: Id[Unit] = printLine("foo").foldMap(InOut2Id)
}
