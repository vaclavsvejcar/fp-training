package free.algebra

import cats.InjectK
import cats.free.Free

/**
  * Algebra for In/Out related operations.
  *
  * @tparam T operation return type
  */
sealed trait InOutA[T]
object InOutA {
  case class PrintLine(line: String) extends InOutA[Unit]
}

/**
  * [[InOutA]] algebra lifted into Free monads.
  *
  * @param I instance of `InjectK`
  * @tparam F final algebra
  */
class InOut[F[_]](implicit I: InjectK[InOutA, F]) {
  import InOutA._

  def printLine(line: String): Free[F, Unit] = Free.inject[InOutA, F](PrintLine(line))
}

/** Helper instances */
object InOut {
  implicit def instance[F[_]](implicit I: InjectK[InOutA, F]): InOut[F] = new InOut
}
