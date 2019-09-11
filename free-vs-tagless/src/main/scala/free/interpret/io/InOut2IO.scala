package free.interpret.io

import cats.effect.IO
import cats.~>
import free.algebra.InOutA

object InOut2IO extends (InOutA ~> IO) {
  override def apply[A](fa: InOutA[A]): IO[A] = fa match {
    case InOutA.PrintLine(line) => IO { println(line) }
  }
}
