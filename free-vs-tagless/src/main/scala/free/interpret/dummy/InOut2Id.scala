package free.interpret.dummy

import cats.{Id, ~>}
import free.algebra.InOutA

object InOut2Id extends (InOutA ~> Id) {
  override def apply[A](fa: InOutA[A]): Id[A] = fa match {
    case InOutA.PrintLine(line) => println(line)
  }
}
