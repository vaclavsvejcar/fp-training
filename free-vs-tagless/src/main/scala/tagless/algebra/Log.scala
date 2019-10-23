package tagless.algebra

/**
  * Logging operations.
  * @tparam F wrapper type
  */
trait Log[F[_]] {
  def info(message: String): F[Unit]
}
