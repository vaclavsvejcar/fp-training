package tagless.algebra

/**
  * In/Out related operations.
  * @tparam F wrapper type
  */
trait InOut[F[_]] {
  def printLine(line: String): F[Unit]
}
