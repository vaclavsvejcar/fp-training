package free.algebra

import cats.InjectK
import cats.free.Free

sealed trait InOutA[A]
object InOutA {
  case class PrintLine(line: String) extends InOutA[Unit]
}

class InOut[F[_]](implicit I: InjectK[InOutA, F]) {
  import InOutA._

  def printLine(line: String): Free[F, Unit] = Free.inject[InOutA, F](PrintLine(line))
}

object InOut {
  implicit def instance[F[_]](implicit I: InjectK[InOutA, F]): InOut[F] = new InOut
}
