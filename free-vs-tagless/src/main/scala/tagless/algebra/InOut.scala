package tagless.algebra

trait InOut[F[_]] {
  def printLine(line: String): F[Unit]
}

object InOut {
  def apply[F[_]](implicit I: InOut[F]): InOut[F] = I
}
