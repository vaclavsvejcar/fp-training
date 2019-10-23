package tagless.algebra

trait Log[F[_]] {
  def info(message: String): F[Unit]
}
