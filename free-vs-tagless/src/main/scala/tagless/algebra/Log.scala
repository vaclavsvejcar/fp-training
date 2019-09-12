package tagless.algebra

trait Log[F[_]] {
  def info(message: String): F[Unit]
}

object Log {
  def apply[F[_]](implicit I: Log[F]): Log[F] = I
}
