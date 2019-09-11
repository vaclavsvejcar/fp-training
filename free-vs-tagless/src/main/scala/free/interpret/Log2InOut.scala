package free.interpret

import cats.free.Free
import cats.{Monad, ~>}
import free.FreeInOutA
import free.algebra.{InOut, InOutA, LogA}

class Log2InOut(implicit inOut: InOut[InOutA]) extends (LogA ~> FreeInOutA) {
  override def apply[A](fa: LogA[A]): FreeInOutA[A] = fa match {
    case LogA.Info(message) => inOut.printLine(s"[INFO]: $message")
  }
}

object Log2InOut {
  def interp[M[_]: Monad](inOutInterp: InOutA ~> M): LogA ~> M = new Log2InOut andThen Free.foldMap(inOutInterp)
}
