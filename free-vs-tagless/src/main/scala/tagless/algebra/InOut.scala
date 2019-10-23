package tagless.algebra

trait InOut[F[_]] {
  def printLine(line: String): F[Unit]
}
